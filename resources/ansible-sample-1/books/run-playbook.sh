#!/bin/sh

#echo "$VAULT_PASS" > ~/.vault_pass.txt
#mkdir ~/.ssh
#ansible-vault --vault-password-file ~/.vault_pass.txt view ansible/ssh_key.txt.secret > ~/.ssh/id_rsa
#chmod 0600 ~/.ssh/id_rsa
#
#ansible-playbook -e "build_sha=$GITHUB_SHA" --vault-password-file ~/.vault_pass.txt -i ansible/hosts ansible/deploy.yml


export ANSIBLE_HOST_KEY_CHECKING=False
export FAPI_HOSTS="devservers"
ansible-playbook --vault-password-file ~/vault-pw.txt -i inventory.ini \
 -e FAPI_HOSTS=$FAPI_HOSTS \
 playbook.yml