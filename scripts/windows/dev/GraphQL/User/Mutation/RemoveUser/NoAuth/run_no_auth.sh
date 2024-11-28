#!/bin/bash

# Step 1: Create a timestamped filename for the response
today=`date '+%m_%d_%Y_%H_%M_%S'`
filename="RemoveUserResponse_$today.json"

# Step 2: Read JSON request body from file and send the add user request
curl -X POST "http://localhost:8070/graphql" \
  -H "Content-Type: application/json" \
  -d @RemoveUser.json \
  -o $filename

#curl -X POST "http://localhost:8070/graphql" \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $ACCESS_TOKEN" \
#  -d @RemoveUser.json \
#  -o $filename

# Step 4: Check the response
echo "Response received. Check $filename for details."
cat $filename

