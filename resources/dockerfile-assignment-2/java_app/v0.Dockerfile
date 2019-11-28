# use this empty Dockerfile to build your assignment

# This dir contains a Spring Boot (java) web application, you need to get it running in a container
# No modifications to the app should be necessary, only edit this Dockerfile

# Overview of this assignment
# use the instructions below to create a working Dockerfile
# once Dockerfile builds correctly, start container locally to make sure it works
# access the web application from your browser at http://<docker-host-ip>:8090
# then ensure image is named properly for your Docker Hub account with a new repo name
# push to Docker Hub, then go to https://hub.docker.com and verify
# then remove local image from cache
# then start a new container from your Hub image, and watch how it auto downloads and runs
# test again that it works 
# access again the web application from your browser at http://<docker-host-ip>:8090


# in the end you should be using FROM, RUN, WORKDIR, COPY, EXPOSE, and CMD instructions 

# Instructions from the application developers
# - The base image must have the java openjdk 11 tool already installed and must be based on debian linux (buster or stretch).
FROM openjdk:11

# - This application listens on TCP port 8090
# - Inform Docker that the application inside the container listens on port 8090
EXPOSE 8090

# - then it should create directory /app for application files with 'mkdir /app'
RUN mkdir /app

# - Set the working directory to '/app' for any RUN, CMD, ENTRYPOINT, COPY and ADD instructions
# - This is where the source file will be copied
WORKDIR /app

# - Copy all source files from the current directory on the host to the '/app' directory
# - Note that we want to copy the files located on the docker host under the the current directory (java_app)
# - to the docker image filesytem under the '/app' directory
COPY . /app

# From the /app directory execute the "./gradlew build" command
# The build command will generate the jar file that will be later used the run the application
# The jar file will be available at "build/libs/java_app-0.0.1.jar" path
RUN ./gradlew build

# Run the jar file
# Use the "java -jar <jar-filename>" command to start the application
# Note this must be the default (MAIN) application running inside the container
CMD ["java", "-jar", "/app/build/libs/java_app-0.0.1.jar"]