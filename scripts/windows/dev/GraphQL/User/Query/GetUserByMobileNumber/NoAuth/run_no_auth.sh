#!/bin/bash

# Step 1: Create a timestamped filename for the response
today=`date '+%m_%d_%Y_%H_%M_%S'`
filename="GetUserByMobileNumberResponse_$today.json"

# Step 2: Read JSON request body from file and send the create user request
curl -X POST "http://localhost:8070/graphql" \
  -H "Content-Type: application/json" \
  -d @GetUserByMobileNumber.json \
  -o $filename

#curl -X POST "http://localhost:8070/graphql" \
#  -H "Content-Type: application/json" \
#  -H "Authorization: Bearer $ACCESS_TOKEN" \
#  -d @GetUserByMobileNumber.json \
#  -o $filename

# Step 3: Check the response
echo "Response received. Check $filename for details."
cat $filename

