# SAJ bot

This project provides a small Spring Boot application that aims to optimise the operation of the SAJ H2 Home Battery.  
Using weather forecasts and electricity prices the bot decides when the battery should charge or discharge.  
All collected sensor data and decisions are stored in a local H2 database so that the algorithm can be tuned in the future.

## Features
- REST API that exposes the current battery level and collected data.
- Reads a weather API based on the location configured in `application.yml`.
- Reads electricity prices from a configurable endpoint.
- Simple "brain" that periodically evaluates the situation and charges/discharges the battery.
- Stores data points in an embedded H2 database.
- Built using Java 17, Maven and Spring Boot.

## Maven proxy settings

Maven requires network access to download dependencies. The included
`settings.xml` configures a proxy so builds work behind a firewall. Use
the file with the `-s` flag whenever running Maven.

## Running locally

```
mvn spring-boot:run
```

After startup the REST endpoints will be available on `http://localhost:8080/api`.

## Running on Umbrel
Umbrel allows running Docker based applications. Build the container and deploy it via the Umbrel interface:

```
mvn -s settings.xml package
docker build -t sajbot .
docker run -p 8080:8080 sajbot
```

You can then access the web UI at `http://umbrel.local:8080`.
