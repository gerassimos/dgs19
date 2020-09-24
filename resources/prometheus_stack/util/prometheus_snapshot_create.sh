#!/bin/bash

##   Basic script to create prometheus snanshot of the DB (TSDB data)

set -e # automatic exit on error
typeset -r dirOfThisScript=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
source ${dirOfThisScript}/util_common.sh

printInfoMessage "Start"

#generate the snapshot
cmdCreateSnapshot="docker exec -it prometheus wget  -qO- --post-data="" http://localhost:9090/api/v1/admin/tsdb/snapshot"
jsonRes=$(eval ${cmdCreateSnapshot})
echo $jsonRes
#list the /prometheus/snapshots

snapshotCreated=$(echo $jsonRes | grep "success" | wc -l)
if [ ${snapshotCreated} -gt 0 ]; then
  printInfoMessage "prometheus DB snapshot successfully created "
  # list snapshots
  docker exec -it prometheus ls -la /prometheus/snapshots

  # dervice snapshot derictory name
  snapshotDerictoryName=$(echo $jsonRes | sed 's/.*name":"\(.*\)\("}}\)/\1/')
  printInfoMessage "New Snapshot Directory ${snapshotDerictoryName}"

  # create tar.gz file from the snapshot derictory
  printInfoMessage "Createing tar.gz file from the snapshot derictory"
  docker exec -it prometheus sh -c "cd /prometheus/snapshots && tar -zcvf ${snapshotDerictoryName}.tar.gz ${snapshotDerictoryName}"

  # Copy tar.gz file from the container to the host
  printInfoMessage "Copy <snapshot>.tar.gz  file from the container to the host"
  docker cp prometheus:/prometheus/snapshots/${snapshotDerictoryName}.tar.gz .

  # Delete <snapshot>.tar.gz in the container
  printInfoMessage "Delete <snapshot>.tar.gz in the container"
  docker exec -it prometheus sh -c "cd /prometheus/snapshots && rm ${snapshotDerictoryName}.tar.gz"

else
  printError "Failed to create prometheus DB snapshot"
fi

printInfoMessage "End"