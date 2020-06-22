#!/bin/bash

# SECONDS=0
go build -o app .

# duration="Duration: $(($SECONDS / 3600))hrs $((($SECONDS / 60) % 60))min $(($SECONDS % 60))sec"
# echo "${duration}"