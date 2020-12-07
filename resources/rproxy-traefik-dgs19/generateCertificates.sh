#!/bin/bash

## Basic script to generate SSL certificates, Docker secrets and config for rproxy

set -e # automatic exit on error
typeset -r dirOfThisScript=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd ${dirOfThisScript}

source rproxy.properties
export RPROXY_TAG
emailAddress=${1:-support@mydomain.com}

countProxySecrets=$(docker secret ls | grep "proxy_ssl_key_${RPROXY_TAG}" | wc -l)
if [ ${countProxySecrets} -gt 0 ]; then
  action_msg="docker secrets for proxy ssl keys already exists"
  echo "${action_msg}"
  action_msg="Exiting"
  echo "${action_msg}"
  exit ${SUCCESS_CODE}
fi

# Deleting old ssl dir if exist
if [ -d ${dirOfThisScript}/ssl ]; then
  # Take action if $DIR exists. #
  action_msg="Deleting old ssl dir..."
  echo "${action_msg}"
  rm -rfv ${dirOfThisScript}/ssl
fi

####################################################
# Gernerate SSL certificates
####################################################

# Create SSL certificates for CA
action_msg="Create SSL certificates for CA"
echo "Start: ${action_msg}"
mkdir "${dirOfThisScript}"/ssl

echo "generating ca.key"
openssl genrsa -out "${dirOfThisScript}"/ssl/ca.key 2048
echo "generating ca.csr"
openssl req -new -key "${dirOfThisScript}"/ssl/ca.key -subj "/CN=SWARM-CA" -out "${dirOfThisScript}"/ssl/ca.csr
sleep 3
echo "generating ca.crt"
openssl x509 -req -days 3650 -in ${dirOfThisScript}/ssl/ca.csr -signkey ${dirOfThisScript}/ssl/ca.key -out ${dirOfThisScript}/ssl/ca.crt
echo "display ca.crt"
openssl x509 -in "${dirOfThisScript}"/ssl/ca.crt -text -noout

# cd ${dirOfThisScript}/ssl
echo "generating rproxy.key"
openssl genrsa -out "${dirOfThisScript}"/ssl/rproxy.key 2048
echo "generating rproxy.csr"
openssl req -new -key "${dirOfThisScript}"/ssl/rproxy.key \
  -subj "/C=GR/ST=Attica/L=Athens/O=my-org/OU=my-group/CN=node01.mydomain.com/emailAddress=${emailAddress}" \
  -out "${dirOfThisScript}"/ssl/rproxy.csr

echo "generating rproxy.crt"
openssl x509 -req -in "${dirOfThisScript}"/ssl/rproxy.csr -CA "${dirOfThisScript}"/ssl/ca.crt -CAkey "${dirOfThisScript}"/ssl/ca.key -CAcreateserial -CAserial "${dirOfThisScript}"/ssl/ca.serial -out "${dirOfThisScript}"/ssl/rproxy.crt
echo "display rproxy.crt"
openssl x509 -in "${dirOfThisScript}"/ssl/rproxy.crt -text -noout

echo "generating admin-user.key"
openssl genrsa -out "${dirOfThisScript}"/ssl/admin-user.key 2048
echo "generating admin-user.csr"
openssl req -new -key "${dirOfThisScript}"/ssl/admin-user.key \
  -subj "/CN=ADMIN_USER" \
  -out "${dirOfThisScript}"/ssl/admin-user.csr
echo "generating admin-user.crt"
openssl x509 -req -in "${dirOfThisScript}"/ssl/admin-user.csr -CA "${dirOfThisScript}"/ssl/ca.crt -CAkey "${dirOfThisScript}"/ssl/ca.key -CAcreateserial -CAserial "${dirOfThisScript}"/ssl/ca.serial -out "${dirOfThisScript}"/ssl/admin-user.crt
echo "display admin-user.crt"
openssl x509 -in "${dirOfThisScript}"/ssl/admin-user.crt -text -noout


####################################################
# Gernerate Docker secrets and config for traefik
####################################################
# create docker secrets to store ssl certificates
action_msg="create docker secrets to store ssl certificates"
echo "Start: ${action_msg}"
# the following ca.crt file is needed if we want to enable the mTLS
docker secret create proxy_ssl_ca_${RPROXY_TAG} ${dirOfThisScript}/ssl/ca.crt
docker secret create proxy_ssl_key_${RPROXY_TAG} ${dirOfThisScript}/ssl/rproxy.key
docker secret create proxy_ssl_crt_${RPROXY_TAG} ${dirOfThisScript}/ssl/rproxy.crt
docker secret ls | grep proxy_ssl
echo "End: ${action_msg}"

# create docker config to store traefik dynamic configuration file
action_msg="create docker config to store traefik dynamic configuration file"
echo "Start: ${action_msg}"
docker config  create proxy_dynamic_config_${RPROXY_TAG} ${dirOfThisScript}/traefik.yml
docker config ls | grep proxy_dynamic_config
echo "End: ${action_msg}"

echo "End: ${action_msg}"


