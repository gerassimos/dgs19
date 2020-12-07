# Basic example - Client Authentication (mTLS) with traefik reverse proxy

---

## Usage
  - Execute the `deploy.sh` script to generate the SSL keys and deploy the Docker stack. 
  - Execute the `testCertificates.sh` script to test the client communication using SSL keys.

---

## key points:
- SSL client and server certificates will be generated from the deploy script (available under the ssl directory)
- traefik is configured as reverse proxy
- traefik dynamic configuration file is traefik.yml is loaded as Docker config
- All SSL certificates are defines as Docker secrets 
- The CA root certificates generated under the ssl directory are ca.crt and ca.key
- The traefik server certificates generated under the ssl directory are rproxy.crt and rproxy.key
- The User "Admin" certificates generated under the ssl directory are admin-user.crt and admin-user.key
