FROM openjdk:11-jre

COPY build/libs/java_app-0.0.1.jar /app.jar

# Run the jar file
# Inlude the "$JAVA_OPTIONS" parameter to set java options from the env variables

# CMD java -XX:+PrintFlagsFinal $JAVA_OPTIONS -jar /app.jar
CMD java -jar /app.jar
