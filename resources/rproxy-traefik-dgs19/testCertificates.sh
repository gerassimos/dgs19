#!/bin/bash

## test mTLS client with curl

curl --key ./ssl/admin-user.key --cert ./ssl/admin-user.crt --cacert ./ssl/ca.crt \
  --header "Content-Type: application/json" --header "Host: my-web-app.local.swarm" \
  --request GET  --resolve node01.mydomain.com:8093:127.0.0.1 "https://node01.mydomain.com:8093"
