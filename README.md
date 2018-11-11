# Bootapps Dashboard

A simple dashboard for displaying health status and application info of multiple Spring Boot applications.

## Requirements

- JDK 8
- ECMAScript 6 compatible browser

## Local Development

Run from source via Spring-Boot-Maven-Plugin:

```
./mvnw spring-boot:run
```

Or build and run from packaged JAR:

```
./mvnw package && java -jar target/bootapps-dashboard-1.0-SNAPSHOT.jar
```


## Installation

Either build the JAR from [source](https://github.com/pboessling/bootapps-dashboard/releases) or download the latest 
[release](https://github.com/pboessling/bootapps-dashboard/releases) and copy the JAR file to the desired folder.
Adapt the configuration as needed. Then Start the application with the `java -jar` command. 

Example:
```
java -jar bootapps-dashboard-1.0-SNAPSHOT.jar
```

## Configuration

### server

The `server` namespace contains standard Spring Boot application properties for configuring the embedded Tomcat server, 
in which the webapp is running.

| Property  | Default Value | Description |
| ------------- | ------------- | ------------- |
| port  | 8080  | TBD |

Example:
```yaml
server:
  port: 8080
```

### dashboard

The `dashboard` namespace contains application properties for configuring the Bootapps Dashboard application.

| Property  | Default Value | Description |
| ------------- | ------------- | ------------- |
| autoreload | false | TBD |
| autoreload-interval | 30000 | TBD |
| bootapps | \<none\> | TBD |

Example:
```yaml
dashboard:
  autoreload: false
  autoreload-interval: 30000
  bootapps:
    dev:
      host1:
      - id: bootapps-dashboard
        health-endpoint-url: http://localhost:8080/actuator/health
        info-endpoint-url: http://localhost:8080/actuator/info
      - id: demo-app
        health-endpoint-url: http://localhost:8081/actuator/health
        health-endpoint-username: user
        health-endpoint-password: password
        info-endpoint-url: http://localhost:8081/actuator/info
        info-endpoint-username: user
        info-endpoint-password: password
      host2:
      - id: demo-app
        health-endpoint-url: http://localhost:8081/actuator/health
        health-endpoint-username: user
        health-endpoint-password: password
        info-endpoint-url: http://localhost:8081/actuator/info
        info-endpoint-username: user
        info-endpoint-password: password
    prod:
      host1:
      - id: demo-app
        health-endpoint-url: http://localhost:8081/actuator/health
        health-endpoint-username: user
        health-endpoint-password: password
        info-endpoint-url: http://localhost:8081/actuator/info
        info-endpoint-username: user
        info-endpoint-password: password
```

## Changelog

**1.0**

* Initial release.

## License

[MIT](LICENSE)

## Author Information

Created by [Philippe Bößling](https://www.gihub.com/pboessling).