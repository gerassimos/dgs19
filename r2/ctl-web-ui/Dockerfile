# ref
# https://docs.docker.com/language/golang/build-images/#multi-stage-builds

# Build the application from source
FROM golang:1.21 AS build-stage

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

#COPY *.go ./
COPY . ./

RUN CGO_ENABLED=0 GOOS=linux go build -C cmd -o /app/bin

# Run the tests in the container
FROM build-stage AS run-test-stage
RUN go test -v ./...

# Deploy the application binary into a lean image
FROM gcr.io/distroless/base-debian11 AS build-distroless-debian

WORKDIR /app
COPY --from=build-stage /app/bin /app/bin
COPY --from=build-stage /app/web/templates /app/web/templates
COPY --from=build-stage /app/web/assets /app/web/assets

EXPOSE 8080

USER nonroot:nonroot

ENTRYPOINT [ "/app/bin" ]

FROM alpine:3.20.1 as build-alpine
ARG USER=app-user
ARG UID=1100
RUN adduser --disabled-password --gecos "" \
    --home "/app" \
    --no-create-home \
    --uid "$UID" \
    "$USER"

EXPOSE 8080

WORKDIR /app
COPY --from=build-stage /app/bin /app/bin
COPY --from=build-stage /app/web/templates /app/web/templates
COPY --from=build-stage /app/web/assets /app/web/assets

RUN ls -la /app
RUN pwd


RUN chown -R ${UID}:${UID} /app
USER ${USER}

ENTRYPOINT [ "/app/bin" ]
