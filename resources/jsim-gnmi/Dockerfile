FROM openjdk:11 AS builder

# - This application listens on TCP port 4567
# - Inform Docker that the application inside the container listens on port 8090
EXPOSE 4567

# - then it should create directory /app for application files with 'mkdir /app'
RUN mkdir /app

# - Set the working directory to '/app' for any RUN, CMD, ENTRYPOINT, COPY and ADD instructions
# - This is where the source file will be copied
WORKDIR /app

# Download and Install the gradle tool
# Copy only the files requiered to download and Install the gradle tool
# This is to create image layers that do not change very often
COPY gradlew /app/
COPY gradle /app/gradle
RUN ./gradlew --version

# Download the Java library dependencies (jars)
# This is to create image layers that do not change very often
COPY build.gradle /app/build.gradle
RUN ./gradlew dependencies

# List the dependencies
# To verify that the dependencies have been actually downloaed
# RUN ls -la ~/.gradle/caches/modules-2/files-2.1/

# - Copy all source files from the current directory on the host to the '/app' directory
# - Note that we want to copy the files located on the docker host under the the current directory (java_app)
# - to the docker image filesytem under the '/app' directory
COPY src /app/src

# From the /app directory execute the "./gradlew build" command
# The build command will generate a single the jar file that will be later used the run the application
RUN ./gradlew build

FROM openjdk:11-jre
COPY --from=builder /app/build/libs/app.jar .
CMD java -jar app.jar
