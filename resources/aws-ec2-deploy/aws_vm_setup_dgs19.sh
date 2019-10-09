#!/bin/bash
echo 'dgs19' | passwd ec2-user --stdin
cp /etc/ssh/sshd_config /etc/ssh/sshd_config.bck.original
sed -i 's/^PasswordAuthentication no/PasswordAuthentication yes/' /etc/ssh/sshd_config

# Ref:
# https://stackoverflow.com/questions/55880517/how-can-i-create-an-ami-that-uses-password-authentication
cp /etc/cloud/cloud.cfg /etc/cloud/cloud.cfg.bck.original
sed -i 's/lock_passwd: true/lock_passwd: false/' /etc/cloud/cloud.cfg
sed -i 's/ssh_pwauth: .*false/ssh_pwauth:   true/' /etc/cloud/cloud.cfg

yum update -y
amazon-linux-extras install -y docker
service docker start
chkconfig docker on
usermod -a -G docker ec2-user
curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
curl -L https://raw.githubusercontent.com/docker/compose/1.24.1/contrib/completion/bash/docker-compose -o /etc/bash_completion.d/docker-compose

yum install -y git