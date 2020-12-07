#!/bin/bash

##   Basic script to deploy and test web app with the usage of traefik reverse proxy 

set -e # automatic exit on error
typeset -r dirOfThisScript=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

cd ${dirOfThisScript}

./generateCertificates.sh
./docker_stack_deploy.sh
