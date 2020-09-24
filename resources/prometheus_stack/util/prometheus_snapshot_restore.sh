#!/bin/bash

##   Basic script to restore prometheus snanshot of the DB (TSDB data)

if [ $# -lt 1 ]
then
  echo "Usage: $0 "
  echo "<Name of the DB snapshot file to restore.  >"
  echo ""
  echo "NOTE: the DB snapshot file must be located in the same directory as this script"
  echo "Example:"
  echo "./prometheus_snapshot_restore.sh 20200922T142415Z-50d4397dca20e3f9.tar.gz"

  exit 1
fi

set -e # automatic exit on error
typeset -r dirOfThisScript=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
source ${dirOfThisScript}/util_common.sh

snapshotTarFilePath=$1

printInfoMessage "Start"

#verify the that the specified file exist
if [ -f "${snapshotTarFilePath}" ]; then
  action_msg="Verified that the DB dump file ${snapshotTarFilePath} is exists on the system $(hostname)"
  printInfoMessage "${action_msg}"
else
  action_msg="File ${snapshotTarFilePath} Not found on the system $(hostname)"
  printError "${action_msg}"
  exit $FAILURE_CODE
fi

# delete original DB files
printInfoMessage "delete original DB files"
docker exec -it prometheus sh -c "rm -rvf /prometheus/*"

# copy tar.gz file
printInfoMessage "copy the ${snapshotTarFilePath} file in the prometheus container"
docker cp "${snapshotTarFilePath}" prometheus:/prometheus

# untar the tar.gz file
printInfoMessage "untar the ${snapshotTarFilePath} file"
docker exec -it prometheus sh -c "tar -zxvf ${snapshotTarFilePath} "

# move snapshot files under the prometheus direcotry
snapshotDerictoryName=${snapshotTarFilePath/".tar.gz"/}
printInfoMessage "move snapshot files from ${snapshotDerictoryName} under the prometheus direcotry"
docker exec -it prometheus sh -c "mv -v ${snapshotDerictoryName}/* . "

# delete and start a new prometheus container
printInfoMessage "delete and start a new prometheus container"
docker rm -f prometheus
docker-compose -f ../docker-compose.yml up -d

printInfoMessage "End"