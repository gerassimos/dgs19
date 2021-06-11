#!/bin/bash
#
# Copyright 2019 ADVA Optical Networking SE. All rights reserved.
#
# Owner: gerassimosm
#
# $Id: $

if [ $# -lt 2 ]
then
  echo "Usage: $0 "
  echo "< admin password == API_TOKEN >"
  echo "< job-name-to-restore => xml file with the same name is expected >"
  exit 1
fi

API_TOKEN=$1
JOB_NAME=$2
JOB_XML_FILE=${JOB_NAME}.xml

if [ -f ${JOB_XML_FILE} ]; then
   echo "Found ${JOB_XML_FILE}"
else
   echo "File ${JOB_XML_FILE} does not exist."
   exit 1
fi

crumb="$(curl -u "${API_TOKEN}:admin" -s 'http://127.0.0.1:8180/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)')"
echo "crumb ${crumb}"

cat ${JOB_XML_FILE} | curl -X POST -H "$crumb" "http://admin:${API_TOKEN}@127.0.0.1:8180/createItem?name=${JOB_NAME}" --header "Content-Type: application/xml" -d @-