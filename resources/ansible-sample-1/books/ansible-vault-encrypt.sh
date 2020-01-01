#!/bin/sh

ansible-vault --vault-password-file vault-pw.txt \
    encrypt inventory.decrypted.ini \
    --output inventory.ini