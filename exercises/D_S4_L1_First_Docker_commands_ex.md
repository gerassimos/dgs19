Exercise

Description:

1.1 Show the Docker version information

```console
# docker version
Client:
 Version:       18.03.0-ce
 API version:   1.37
 Go version:    go1.9.4
 Git commit:    0520e24302
 Built: Fri Mar 23 08:31:36 2018
 OS/Arch:       windows/amd64
 Experimental:  false
 Orchestrator:  swarm

Server:
 Engine:
  Version:      18.05.0-ce
  API version:  1.37 (minimum version 1.12)
  Go version:   go1.10.1
  Git commit:   f150324
  Built:        Wed May  9 22:20:42 2018
  OS/Arch:      linux/amd64
```  
  
---  

1.2 Display system-wide information:

```console
# docker info
Containers: 5
 Running: 0
 Paused: 0
 Stopped: 5
Images: 24
Server Version: 18.05.0-ce
Storage Driver: aufs
 Root Dir: /mnt/sda1/var/lib/docker/aufs
 Backing Filesystem: extfs
 Dirs: 134
 Dirperm1 Supported: true
Logging Driver: json-file
Cgroup Driver: cgroupfs
Plugins:
 Volume: local
 Network: bridge host macvlan null overlay
 Log: awslogs fluentd gcplogs gelf journald json-file logentries splunk syslog
Swarm: inactive
... output truncated 
```

---

1.3 Verify the "Swarm" status using the "docker info" command

```console
# docker info | grep Swarm
Swarm: inactive
```

---

1.4 Display the complete list of Docker commands
```console
# docker <Enter>
Usage:  docker COMMAND

A self-sufficient runtime for containers

Options:
      --config string      Location of client config files (default
                           "C:\\Users\\GerassimosM\\.docker")
...

Management Commands:
  
  container   Manage containers
  image       Manage images
  network     Manage networks
  ...

Commands:
  attach      Attach local standard input, output, and error streams to a running container
  build       Build an image from a Dockerfile
  commit      Create a new image from a container's changes
  cp          Copy files/folders between a container and the local filesystem
...
```
---

1.5 Explain the difference between the "docker info" and the "docker system info" commands:

 - Both commands will display the same information. 
 - The `docker info` command is the old way to display system information.
 - The `docker system info` is the new way to display system information which make use of the new management format:   
### `docker <mng-command> <command>`.

