# the original ssh URL containes a @ character
# ip172-18-0-21-bvfko8ag770000b3ui2g@direct.labs.play-with-docker.com
# we need to replace the '@' with a '.'
# ip172-18-0-21-bvfko8ag770000b3ui2g.direct.labs.play-with-docker.com
DOCKER_HOST=ip172-18-0-21-bvfko8ag770000b3ui2g.direct.labs.play-with-docker.com docker ps

# context
# example with context usage
docker context create --docker "host=tcp://ip172-18-0-21-bvfko8ag770000b3ui2g.direct.labs.play-with-docker.com:2375" pwd
docker context use pwd
docker ps

# ip172-18-0-67-bvfko8ag770000b3ui2g@direct.labs.play-with-docker.com
# ip172-18-0-67-bvfko8ag770000b3ui2g.direct.labs.play-with-docker.com
pwd_url=host=tcp://ip172-18-0-67-bvfko8ag770000b3ui2g.direct.labs.play-with-docker.com:2375
docker context create --docker "${pwd_url}" pwd
docker context use pwd


# SSH
# remote connect via ssh
# key based access and ~/.ssh/config are needed
# The "srv1.gm-vm103" is the string defined as Host srv1.gm-vm103
docker context create --docker "host=ssh://srv1.gm-vm103" ssh
