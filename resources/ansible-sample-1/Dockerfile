FROM alpine

ENV ANSIBLE_HOST_KEY_CHECKING=False

RUN apk add ansible gcc python3-dev libc-dev libffi-dev openssl-dev
RUN pip3 install --upgrade paramiko

RUN apk add openssh

CMD tail -f /dev/null
