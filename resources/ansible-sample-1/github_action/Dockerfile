FROM alpine:3.11.2

ENV ANSIBLE_HOST_KEY_CHECKING=False

RUN apk add ansible=2.9.1-r0 \
    gcc=9.2.0-r3 python3-dev=3.8.1-r0 libc-dev=0.7.2-r0 libffi-dev=3.2.1-r6 openssl-dev=1.1.1d-r3
RUN pip3 install --upgrade paramiko==2.7.1

COPY entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

# CMD tail -f /dev/null
