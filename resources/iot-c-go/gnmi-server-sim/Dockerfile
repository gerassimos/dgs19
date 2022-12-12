FROM alpine:3.17.0

ARG TAG
ARG USER=app-user
ARG UID=1100

# Arbitrary labels applied to the image project
LABEL build="${TAG}"
LABEL dgs19.project="gnmi-go"
LABEL module="gnmi-server-sim"

RUN mkdir /app

RUN adduser --disabled-password --gecos "" \
    --home "/app" \
    --no-create-home \
    --uid "$UID" \
    "$USER"

COPY app /app

RUN chown -R ${UID}:${UID} /app

USER ${user}

CMD [ "/app/app" ]
#CMD [ "sh", "-c", "sleep 1d" ]
