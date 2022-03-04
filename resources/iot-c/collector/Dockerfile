FROM openjdk:11.0.14.1-jdk-buster

ARG JAR_FILE
ARG IOTC_TAG

# Arbitrary labels applied to the image project
LABEL dgs19.project="iot-c"
LABEL iot-c.module="collector"
LABEL iot-c.tag="${IOTC_TAG}"
LABEL jar.file="${JAR_FILE}"

# install the psql client
# RUN apt-get update && apt-get install -y postgresql-client=10+190

# this script will wait for postgres to accept connections
# then will execute all arguments as a linux command
# COPY wait-for-postgres.sh /wait-for-postgres.sh

COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# Entry with exec
ENTRYPOINT exec java $JAVA_OPTS  -jar /app.jar