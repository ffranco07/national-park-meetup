#!/bin/bash

if [ -z "$1" ]; then
  echo "Error: MOBILE_NUMBER is required!"
  exit 1
fi

MOBILE_NUMBER=$1
echo "Using MOBILE_NUMBER: $MOBILE_NUMBER"

# Default endpoint and userId
BASE_URL="http://localhost:8070/api/location"
today=$(date '+%m_%d_%Y_%H_%M_%S')
#filename="GetUserLocationResponse_${MOBILE_NUMBER}_$today.json"
filename="GetUserLocationResponse_$today.json"

# Make the GET request and format the response
curl -X GET "$BASE_URL/current?mobileNumber=$MOBILE_NUMBER" -H 'Content-Type: application/json' -H "accept: application/json" | node -e "s=process.openStdin();d=[];s.on('data',function(c){d.push(c);});s.on('end',function(){console.log(JSON.stringify(JSON.parse(d.join('')),null,2));})" >> "$filename";

echo "Response saved to $filename"

#node -e "console.log(JSON.stringify(JSON.parse(require('fs').readFileSync(process.argv[1])), null, 4));" >> "$filename"