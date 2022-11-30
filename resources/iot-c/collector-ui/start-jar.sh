#!/bin/sh

# neet to execute gradle build to generate the jar file
export JAVA_OPTS="-javaagent:../utils/otel/opentelemetry-javaagent.1.20.2.jar"
# export OTEL_TRACES_EXPORTER=logging # console
# export OTEL_METRICS_EXPORTER=logging # console
export OTEL_METRICS_EXPORTER=none # explicitly disable since default is otlp
export OTEL_TRACES_EXPORTER=jaeger
export OTEL_EXPORTER_JAEGER_ENDPOINT=http://localhost:14250
export OTEL_PROPAGATORS=tracecontext,baggage,b3multi
jarFile=collector-ui-latest.jar
java "$JAVA_OPTS" -jar build/libs/"${jarFile}"
