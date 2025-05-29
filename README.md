# quarkus-mcp-server-example

Quarkus example about how to create mcp server which allows to interact with our REST api through Tools definition.

## Prerequisites

* _maven_
* podman
* _java 21_
* goose

## Running the Example

For running the example we need to follow the steps:

```console
$ mvn clean package
```

```console
$ podman run -d --name db-server -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test -e POSTGRES_DB=applications -p 5432:5432 postgres:16
```

```console
$ cd quarkus-applications-ms && mvn quarkus:dev
```

```console
$ curl -i -X POST \
  -F "name=Angel" \
  -F "surname=Miralles" \
  -F "personalId=12345R" \
  -F "country=Spain" \
  http://localhost:8080/application.html
```

```console
goose session --with-extension="java -jar quarkus-mcp-server/target/quarkus-mcp-server-1.0.0-SNAPSHOT.jar"
```

