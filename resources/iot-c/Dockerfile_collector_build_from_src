FROM adoptopenjdk:11.0.4_11-jdk-hotspot-bionic AS builder

# Get gradle distribution
COPY *.gradle gradle.* gradlew /src/
COPY gradle /src/gradle
WORKDIR /src
RUN ./gradlew --version

# download the dependencies ONLY
# Copy a dummy java class to and build
# The purpose is to download the dependencies (jars)
COPY *.gradle gradle.* gradlew /src/
COPY collector/build.gradle /src/collector/build.gradle
COPY common/build.gradle /src/common/build.gradle
COPY common/src/main/java/BuildDependencies.java collector/src/main/java/BuildDependencies.java
RUN ./gradlew build

# build the collector jar
COPY . .
RUN rm collector/src/main/java/BuildDependencies.java
RUN ./gradlew :collector:build

FROM adoptopenjdk:11.0.4_11-jdk-hotspot-bionic
COPY --from=builder /src/collector/build/libs/collector-latest.jar /app.jar
CMD ["java", "-jar", "/app.jar"]