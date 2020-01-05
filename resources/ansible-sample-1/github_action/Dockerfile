FROM alpine

RUN apk add ansible gcc python3-dev libc-dev libffi-dev openssl-dev
RUN pip3 install --upgrade paramiko

RUN apk add openssh

COPY entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

# CMD tail -f /dev/null
