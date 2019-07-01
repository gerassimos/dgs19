# deploy AWS EC2 instances with ansible   

## References 
 - https://www.youtube.com/watch?v=gEX1HbM4KSM
 - https://docs.ansible.com/ansible/latest/modules/ec2_module.html
 - https://boto.readthedocs.io/en/latest/boto_config_tut.html
 
## Installation notes
```console
[root]# yum install python-pip
[root]# pip install boto
[root]# cd /root
[root]# vim .boto
[root]# cat .boto
[Credentials]
aws_access_key_id = <your_access_key_here>
aws_secret_access_key = <your_secret_key_here>
``` 
 