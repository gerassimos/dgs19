#!/bin/sh

# get latest
curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar --output opentelemetry-javaagent.jar

## OR get specific version
## get  v1.26.0
#curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.26.0/opentelemetry-javaagent.jar  --output opentelemetry-javaagent.1.26.0.jar