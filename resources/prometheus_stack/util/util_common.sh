#!/bin/bash

##  common place to define general purpose functions for deployments

typeset -i SUCCESS_CODE=0
typeset -i FAILURE_CODE=1

printError()
{
  # print the error message in red color  with time-stamp
  >&2 echo -e "\e[01;31m`date +%Y-%m-%d_%H:%M:%S` $*\e[0m"
}

printInfoMessage()
{
  echo "`date +%Y-%m-%d_%H:%M:%S`: $@"
}

valid_ip()
{
  local  ip=$1
  local  stat=$FAILURE_CODE

  if [[ $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
    OIFS=$IFS
    IFS='.'
    ip=($ip)
    IFS=$OIFS
    [[ ${ip[0]} -le 255 && ${ip[1]} -le 255 \
    && ${ip[2]} -le 255 && ${ip[3]} -le 255 ]]
    stat=$?
  fi
  if [ $stat -ne $SUCCESS_CODE ]; then
     printError "${ip} is not a valid IPv4 address"
  fi
  return $stat
}