#!/bin/sh

echo "$VAULT_PASS" > ~/vault-pw.txt
echo "$SERVER_KEY_ID_RSA" > ~/id_rsa
mkdir ~/.ssh
mv ~/id_rsa ~/.ssh/id_rsa
chmod 0600 ~/.ssh/id_rsa

echo "GITHUB_SHA $GITHUB_SHA"

export ANSIBLE_HOST_KEY_CHECKING=False
export FAPI_HOSTS="devservers"
ansible-playbook --vault-password-file ~/vault-pw.txt \
 -i resources/ansible-sample-1/books/inventory.ini \
 -e FAPI_HOSTS=$FAPI_HOSTS \
 resources/ansible-sample-1/books/playbook.yml