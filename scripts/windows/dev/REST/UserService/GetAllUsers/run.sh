#!/bin/bash

# Default endpoint and userId
BASE_URL="http://localhost:8070/api/users"
today=$(date '+%m_%d_%Y_%H_%M_%S')
filename="GetAllUsersResponse_$today.json"

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

# Step 3: Make the GET request with JWT Token in Authorization header
curl -X GET "$BASE_URL" -H 'Content-Type: application/json' \
  -H "accept: application/json" \
  -H "Authorization: Bearer $jwt_token" | node -e "s=process.openStdin();d=[];s.on('data',function(c){d.push(c);});s.on('end',function(){console.log(JSON.stringify(JSON.parse(d.join('')),null,2));})" >> "$filename"

echo "Response saved to $filename"