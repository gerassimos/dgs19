FROM adoptopenjdk:11.0.4_11-jdk-hotspot-bionic AS builder

# Get gradle distribution
COPY *.gradle gradle.* gradlew /src/
COPY gradle /src/gradle
WORKDIR /src
RUN ./gradlew --version

# download the dependenices ONLY
# Copy a dymmy java class to and build
# The perpose is to download the dependenices (jars)
COPY *.gradle gradle.* gradlew /src/
COPY collector-ui/build.gradle /src/collector-ui/build.gradle
COPY common/build.gradle /src/common/build.gradle
COPY common/src/main/java/BuildDependencies.java collector-ui/src/main/java/BuildDependencies.java
RUN ./gradlew build

# build the collector-ui jar
COPY . .
RUN rm collector-ui/src/main/java/BuildDependencies.java
RUN ./gradlew :collector-ui:build

FROM adoptopenjdk:11.0.4_11-jdk-hotspot-bionic
COPY --from=builder /src/collector-ui/build/libs/collector-ui-latest.jar /app.jar
CMD ["java", "-jar", "/app.jar"]