#!/bin/bash

# Deafault endpoint
today=`date '+%m_%d_%Y_%H_%M_%S'`
filename="AddUserResponse_$today.json"

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

# Step 3: Read JSON request body from file and send the add user request
curl -X POST "http://localhost:8070/graphql" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $jwt_token" \
  -d @AddUser1.json \
  >> $filename

echo "" >> $filename
curl -X POST "http://localhost:8070/graphql" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $jwt_token" \
  -d @AddUser2.json \
  >> $filename

echo "" >> $filename
curl -X POST "http://localhost:8070/graphql" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $jwt_token" \
  -d @AddUser3.json \
  >> $filename

echo "" >> $filename
curl -X POST "http://localhost:8070/graphql" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $jwt_token" \
  -d @AddUser4.json \
  >> $filename

echo "" >> $filename
curl -X POST "http://localhost:8070/graphql" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $jwt_token" \
  -d @AddUser5.json \
  >> $filename

# Step 4: Check the response
echo "Response received. Check $filename for details."
cat $filename

