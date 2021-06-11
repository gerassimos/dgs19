#!/bin/bash
#
# Copyright 2019 ADVA Optical Networking SE. All rights reserved.
#
# Owner: gerassimosm
#
# $Id: $

crumb=$(curl -u "admin:admin" -s 'http://127.0.0.1:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)')
echo "crumb ${crumb}"

# trigger job
curl -u "admin:admin" -H "$crumb" -X POST http://192.168.99.102:8180/job/dgs19_build_01/buildWithParameters?DELETE_DB_VOLUME=false

# trigger job. the jenkins_admin_u_p variable is defined in the Bindings section of the job
curl -G -u "${jenkins_admin_u_p}" -H "$crumb" -X POST \
"http://127.0.0.1:8080/job/dgs19_test1/buildWithParameters" \
--data-urlencode "PARAM1=test2 aa" \
--data-urlencode "PARAM2=test3 bb"


# backup a job to xml file
JOB_NAME="my-first-job"
curl -s -H "$crumb" http://admin:admin@192.168.99.102:8180/job/${JOB_NAME}/config.xml > ${JOB_NAME}.xml
# import job from xml
cat my-first-job.xml | curl -X POST -H "$crumb" http://admin:admin@127.0.0.1:8180/createItem?name=my-first-job' --header "Content-Type: application/xml" -d @-