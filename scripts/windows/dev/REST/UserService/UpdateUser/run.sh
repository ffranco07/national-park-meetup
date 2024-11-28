#!/bin/bash

if [ -z "$1" ]; then
  echo "Error: MOBILE_NUMBER is required!"
  exit 1
fi

MOBILE_NUMBER=$1
echo "Using MOBILE_NUMBER: $MOBILE_NUMBER"

# Default endpoint
BASE_URL="http://localhost:8070/api/users"
today=$(date '+%m_%d_%Y_%H_%M_%S')
filename="UpdateUserResponse_$today.json"

# Step 1: Generate JWT Token using the /auth/token endpoint
# Assuming the /auth/token endpoint exists and you pass a valid username
token_response=$(curl -s -X POST "http://localhost:8070/auth/token" \
  -H "accept: application/json" \
  -H "Content-Type: application/json" \
  -d '{"username": "prototypeUser"}')

# Step 2: Directly use the token response as JWT token
jwt_token=$token_response

# Check if we got the token
if [ -z "$jwt_token" ]; then
  echo "Failed to obtain JWT token"
  exit 1
fi

echo "Generated JWT Token: $jwt_token"

# Step 3: Make the POST request and format the response
curl -X PUT "$BASE_URL/$MOBILE_NUMBER" -H 'Content-Type: application/json' -H "accept: application/json" -d @UpdateUserRequest.json -H "Authorization: Bearer $jwt_token" | node -e "s=process.openStdin();d=[];s.on('data',function(c){d.push(c);});s.on('end',function(){console.log(JSON.stringify(JSON.parse(d.join('')),null,2));})" >> "$filename";

echo "Response saved to $filename"

#node -e "console.log(JSON.stringify(JSON.parse(require('fs').readFileSync(process.argv[1])), null, 4));" >> "$filename"