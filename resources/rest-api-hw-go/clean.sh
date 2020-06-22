#!/bin/bash

dirOfThisScript=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
echo "go clean"
go clean 

rm -v ${dirOfThisScript}/app

