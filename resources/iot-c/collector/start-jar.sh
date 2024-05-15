#!/bin/sh

# neet to execute gradle build to generate the jar file
export JAVA_OPTS="-javaagent:../utils/otel/opentelemetry-javaagent.2.3.0.jar"
# export OTEL_TRACES_EXPORTER=logging # console
# export OTEL_METRICS_EXPORTER=logging # console
export OTEL_METRICS_EXPORTER=none # explicitly disable since default is otlp
export OTEL_TRACES_EXPORTER=otlp

# By default, the OpenTelemetry Java agent uses OTLP exporter configured to send data to
# OpenTelemetry collector at http://localhost:4317.
# Ref:
# https://github.com/open-telemetry/opentelemetry-java-instrumentation?tab=readme-ov-file#configuring-the-agent
# https://opentelemetry.io/docs/languages/sdk-configuration/otlp-exporter/
export OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317
export OTEL_EXPORTER_OTLP_PROTOCOL=grpc
export OTEL_PROPAGATORS=tracecontext,baggage,b3multi
jarFile=collector-latest.jar
java "$JAVA_OPTS" -jar build/libs/"${jarFile}"
