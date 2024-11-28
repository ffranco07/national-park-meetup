#!/bin/bash

# Default endpoint
BASE_URL="http://localhost:8070/api/users"
today=$(date '+%m_%d_%Y_%H_%M_%S')
filename="GetAllUsersResponse_$today.json";

curl -X GET "$BASE_URL" -H 'Content-Type: application/json' -H "accept: application/json" | node -e "s=process.openStdin();d=[];s.on('data',function(c){d.push(c);});s.on('end',function(){console.log(JSON.stringify(JSON.parse(d.join('')),null,2));})" >> "$filename";

echo "Response saved to $filename"

#node -e "console.log(JSON.stringify(JSON.parse(require('fs').readFileSync(process.argv[1])), null, 4));" >> "$filename"