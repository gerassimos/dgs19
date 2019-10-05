FROM adoptopenjdk:11.0.4_11-jdk-hotspot-bionic

ARG JAR_FILE
# e.g NMS_TAG = 11.1.1-B4708.tar
ARG IOTC_TAG

# Arbitrary labels applied to the image project
LABEL build="${IOTC_TAG}"
LABEL dgs19.project="iot-c"
LABEL iot-c.module="collector"
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