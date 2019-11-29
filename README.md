# Spring Boot Vault Demo

This is a demo application that shows how to use [Spring Cloud Vault Config](http://cloud.spring.io/spring-cloud-vault/)
to retrieve some secret values from [HashiCorp Vault](https://www.vaultproject.io/) and inject them into the application
context using `@Value` annotation in [VaultdemoApplication.java](src/main/java/de/mschoeck/vault/VaultdemoApplication.java).

## Starting Vault server

To simplfy things, we use Vault in [dev mode](https://www.vaultproject.io/docs/concepts/dev-server.html) but this demo can adapted to use a real configuration with authentication and TLS connections, spring-cloud-vault as [some doc](https://github.com/spring-cloud/spring-cloud-vault/blob/master/README.adoc#quick-start) and bash scripts to make it easy.

### Install and launch HashiCorp Vault

With your project set up, you can install and launch HashiCorp Vault.

If you are using a Mac with homebrew, this is as simple as:
~~~
$ brew install vault
~~~

Alternatively, download Vault for your operating system from [https://www.vaultproject.io/downloads.html:](https://www.vaultproject.io/downloads.html)
~~~
$ https://releases.hashicorp.com/vault/1.2.1/vault_1.2.1_darwin_amd64.zip
$ unzip vault_1.2.1_darwin_amd64.zip
~~~

For other systems with package management, such as Redhat, Ubuntu, Debian, CentOS, and Windows, see instructions at [https://www.vaultproject.io/docs/install/index.html.](https://www.vaultproject.io/docs/install/index.html)

Command below starts a Vault server in dev mode with a known initial root token that we can use for dev and tests and listening on http://localhost:8200

~~~
$ vault server -dev -log-level=INFO -dev-root-token-id=00000000-0000-0000-0000-000000000000
~~~

Before using the CLI to configure the vault, you must set this environment variable:

~~~
$ export VAULT_ADDR=http://localhost:8200
$ export VAULT_TOKEN=00000000-0000-0000-0000-000000000000
~~~

Now you can store a configuration key-value pairs inside Vault:

~~~
$ vault kv put secret/vault-demo database.userid=example-user database.password=example-password
~~~


## Running the application

~~~
$ mvn spring-boot:run
~~~

### Check the configuration

Query the credentials:
~~~
$ curl http://localhost:8080/credentials | jq
~~~

The response should look like this:
~~~
{
  "userid": "example-user",
  "password": "example-password"
}
~~~

If the response looks like this:
~~~
{
  "userid": "N/A",
  "password": "N/A"
}
~~~
there is a misconfiguration.

## Advanced 

### Change credentials without restart the application 

Change the credential:

~~~
$ vault kv put secret/vault-demo database.userid=poweruser database.password=poweruser-password
~~~
Check the change in vault:
~~~
$ vault kv get secret/vault-demo
====== Metadata ======
Key              Value
---              -----
created_time     2019-11-29T09:06:40.577966Z
deletion_time    n/a
destroyed        false
version          2

========== Data ==========
Key                  Value
---                  -----
database.password    poweruser-password
database.userid      poweruser
~~~

Trigger the refresh actuator endpoint:
~~~
curl localhost:8080/actuator/refresh -d {} -H "Content-Type: application/json"
~~~

Query the credentials again:
~~~
$ curl http://localhost:8080/credentials | jq
~~~
Now the response should look like this:
~~~
{
  "userid": "poweruser",
  "password": "poweruser-password"
}
~~~