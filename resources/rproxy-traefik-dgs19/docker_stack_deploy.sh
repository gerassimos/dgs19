#!/bin/bash

##   Basic script to deploy Reverse Proxy docker service

if [ $# -lt 0 ]
then
  echo "Usage: $0 "
  echo "<for future use>"
  exit 1
fi

set -e # automatic exit on error
typeset -r dirOfThisScript=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

source rproxy.properties
echo "exporting RPROXY_TAG=${RPROXY_TAG}"
export RPROXY_TAG

# docker deploy all Reverse Proxy services with docker stack
action_msg="deploy Reverse Proxy service with docker stack"
echo "Start ${action_msg}"
docker stack deploy -c docker-stack.yml dgs19-rproxy
echo "End: ${action_msg}"