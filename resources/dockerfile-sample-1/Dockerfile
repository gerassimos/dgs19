# This example is from the official nginx image
FROM debian:stretch-slim
# All Dockerfile must start with the "FROM" instruction
# This is the base linux image that is used as starting point 
# The base image usually is a minimal Linux distribution like debian or alpine
# There are also cases where we want to stat from any "empty" image, 
# in this case we can use 
# FROM scratch

LABEL maintainer="NGINX Docker Maintainers <docker-maint@nginx.com>"
# Labels are a mechanism for applying metadata to images
# A label is a key-value pair, stored as a string. 
# You can specify multiple labels for an image

ENV NGINX_VERSION 1.15.12-1~stretch
ENV NJS_VERSION   1.15.12.0.3.1-1~stretch
# optional environment variable that's used in later lines
# and are also set as environment variable when container is running

RUN apt-get update \
	...
	&& apt-get install --no-install-recommends --no-install-suggests -y \
						nginx=${NGINX_VERSION} \
						nginx-module-xslt=${NGINX_VERSION} \
						nginx-module-geoip=${NGINX_VERSION} \
						nginx-module-image-filter=${NGINX_VERSION} \
						nginx-module-njs=${NJS_VERSION} \
						gettext-base \
	&& rm -rf /var/lib/apt/lists/*
# Install nginx using "apt-get"	
# This is a shell command that is executed from inside the container at build time

# forward request and error logs to docker log collector
RUN ln -sf /dev/stdout /var/log/nginx/access.log \
	&& ln -sf /dev/stderr /var/log/nginx/error.log
# This is a shell command that will create two symbolic links to the stdout and stderr 
# This is to propagate all log messages of nginx to the container log 
# Docker daemon is responsible for handling the containers logs 
# There are also deferent kind of logging driver that can used to handle containers logs 


EXPOSE 80
# expose these ports on the docker virtual network
# By default no TCP/UDP port is open from the container
# We still need to use -p or -P to open/forward these ports on host

STOPSIGNAL SIGTERM
# specify the default linux signal used to stop the container 

CMD ["nginx", "-g", "daemon off;"]
# This is the default command that is execute when we start a container 
# Remember we can override this 