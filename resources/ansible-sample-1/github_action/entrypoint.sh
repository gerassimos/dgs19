#!/bin/sh

echo "$VAULT_PASS" > ~/vault-pw.txt
echo "$SERVER_KEY_ID_RSA" > ~/id_rsa
mkdir ~/.ssh
mv ~/id_rsa ~/.ssh/id_rsa
chmod 0600 ~/.ssh/id_rsa

echo "GITHUB_SHA $GITHUB_SHA"
echo "my key $(cat ~/.ssh/id_rsa)"
echo "id $(id)"
echo "pwd $(pwd)"
echo "home $HOME"
echo "ls -la ~/"
echo "$(ls -la ~/)"

echo "ls -la ~/.ssh"
echo "$(ls -la ~/.ssh)"
ssh ec2-user@54.198.46.177 "pwd"
ping -c3 54.198.46.177
ping -c3 8.8.8.8
ping -c3 srv1.gm76-dev.com

# export ANSIBLE_HOST_KEY_CHECKING=False

ansible-playbook --vault-password-file ~/vault-pw.txt \
 -i $INVENTORY_PATH \
 -e FAPI_HOSTS=$FAPI_HOSTS \
 $PLAYBOOK_PATH

