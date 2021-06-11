FROM jenkins/jenkins:2.170

USER root

RUN curl -O https://bootstrap.pypa.io/get-pip.py  && \
    python get-pip.py && \
    pip install ansible --upgrade
    
    
# Install the latest Docker CE binaries
RUN apt-get update && \
    apt-get -y install apt-transport-https \
      ca-certificates \
      curl \
      gnupg2 \
      software-properties-common && \
    curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
      $(lsb_release -cs) \
      stable" && \
    apt-get update && \
    apt-get -y install docker-ce

# Change the GID of docker group from 999 to 982 to be uqual with the GID of the docker group
# of the host system (centos 7.5)
# The following step is required only if the server is running as jenkins user
# NOTE: the docker group id is not always the same (982) should be defined as variable
# RUN groupmod --gid 982 docker

# The following is required from ansible to run playbooks using username and passwords from inventory files
# instead of public/private keys
# and other useful tools 
RUN apt-get -y install sshpass && \
    apt-get -y install vim && \
    apt-get -y install rsync && \
    echo 'alias ll="ls -lah"' >> /etc/bash.bashrc


# Install openJDK 11
RUN wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | sudo apt-key add - && \
    add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/ && \
    apt-get install -y software-properties-common && \
    apt-get update && \
    apt-get install adoptopenjdk-11-hotspot=11.0.4+11-2

# The following step is required only if the server is running as jenkins user
#RUN usermod -aG docker jenkins

# Uncoment the following to run the server as jenkins user
# USER jenkins
