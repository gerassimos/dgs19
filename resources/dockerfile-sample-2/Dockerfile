FROM debian:stretch-slim
# All Dockerfile must start with the "FROM" instruction
# This is the base linux image that is used as starting point 
# The base image usually is a minimal Linux distribution like debian or alpine
# There are also cases where we want to stat from any "empty" image, 
# in this case we can use 
# FROM scratch

LABEL project="iot-c"
LABEL module="collector"
# Labels are a mechanism for applying metadata to images
# A label is a key-value pair, stored as a string. 
# You can specify multiple labels for an image

ENV PSQL_VERSION=12
# optional environment variable that's used in later lines
# and are also set as environment variable when container is running
# Ref: https://docs.docker.com/engine/reference/builder/#env

RUN apt-get update && apt-get --no-install-recommends -y install \
  postgresql-client=${PSQL_VERSION} \
  ...
  && rm -rf /var/lib/apt/lists/*
# Install postgresql client using "apt-get"
# This is a shell command that is executed from inside the container at build time
# Multiple linux commands are executed on a single RUN line to create a single layer of changes
# The last linux command is used to delete cache data and make the image as small as possible

EXPOSE 80
# expose these ports on the docker virtual network
# By default no TCP/UDP port is open from the container
# We still need to use -p to open/forward these ports on host

COPY my-sh-app.sh app
# To copy a file from the Host (docker client) to the Docker Image
# Here we copy the "binary" of our application to the Docker Image
# When we issue a docker build command, the current working directory is called the build context.
# Ref:
# https://docs.docker.com/develop/develop-images/dockerfile_best-practices/#understand-build-context

CMD ["./app"]
# This is the default command that is execute when we start a container 
# Remember we can override this 