#!/bin/bash
#
# Copyright 2019 ADVA Optical Networking SE. All rights reserved.
#
# Owner: gerassimosm
#
# $Id: $

if [ $# -lt 1 ]
then
  echo "Usage: $0 "
  echo "< admin password == API_TOKEN >"
  exit 1
fi

API_TOKEN=$1
crumb="$(curl -u "${API_TOKEN}:admin" -s 'http://127.0.0.1:8180/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)')"

## declare the job names to backup
declare -a jobs=("dgs19_prod_deploy" "dgs19_test1")
#declare -a jobs=("dgs19_test1")

for job in "${jobs[@]}"
do

   echo "backup job ${job} with crumb ${crumb}"
   curl -s -H "$crumb" http://admin:${API_TOKEN}@192.168.99.100:8180/job/${job}/config.xml > ${job}.xml
done