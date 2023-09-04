FROM eclipse-temurin:17.0.6_10-jdk-focal

ARG JAR_FILE
ARG IOTC_TAG

# Arbitrary labels applied to the image project
LABEL dgs19.project="iot-c"
LABEL iot-c.module="collector-kafka-pg"
LABEL iot-c.tag="${IOTC_TAG}"
LABEL jar.file="${JAR_FILE}"

# install the psql client
# RUN apt-get update && apt-get install -y postgresql-client=10+190

# Note that the opentelemetry-javaagent.jar is added in the context from the gradle build
# it is pointing ../utils/otel/opentelemetry-javaagent.jar
COPY opentelemetry-javaagent.jar opentelemetry-javaagent.jar

COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# Entry with exec
ENTRYPOINT exec java $JAVA_OPTS  -jar /app.jar