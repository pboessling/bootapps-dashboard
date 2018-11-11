# Bootapps Dashboard

A simple dashboard for displaying health status and application info of multiple Spring Boot applications.

![Bootapps Dashboard](docs/img/bootapps-dashboard.png)

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

Then start the application with the `java -jar` command. 

Within the classpath of the application there is an application properties file `application.yml` containing the default
configuration of the application. In order to override the default configuration and to add bootapps to the dashboard, 
you need to provide an external application properties file.

If this external application properties file is called `application.yaml` and located in the same  folder from which the 
application is started, it will automatically be picked up, when starting the application. 

Example:
```
java -jar bootapps-dashboard-1.0-SNAPSHOT.jar
```

If the external properties file has a different name and/or is located in a different folder than the one from which the
application is started, the location of all properties files, both the one on the classpath and the external one in the 
filesystem needs to be provided as additional parameter, in order to be picked up, when starting the application.

Example:
```
java -jar bootapps-dashboard-1.0-SNAPSHOT.jar --spring.config.location=classpath:/application.yml,file:./application.yml
```

## Configuration

### server

The `server` namespace contains standard Spring Boot application properties for configuring the embedded Tomcat server, 
in which the webapp is running.

| Property  | Default Value | Description |
| ------------- | ------------- | ------------- |
| port  | 8080  | The port on which the webapp is running. |

Example:
```yaml
server:
  port: 8080
```

### dashboard

The `dashboard` namespace contains application properties for configuring the Bootapps Dashboard application.

There are some properties with default values, that can be overwritten.

| Property  | Default Value | Description |
| ------------- | ------------- | ------------- |
| autoreload | false | Whether the status of the bootapps on the dashboard should automatically be reloaded. |
| autoreload-interval | 30000 | If autoreload is enabled, the interval in milliseconds between two reloads. |
| bootapps | \<none\> | Connection details for the bootapps to be displayed on the dashboard. |

Example:
```yaml
dashboard:
  autoreload: false
  autoreload-interval: 30000
```

The Bootapps Dashboard assumes, that there are one more environments, which in turn consist of one or more hosts each on
which the Spring Boot apps are hosted on. The environments, hosts, and apps are configured via the property `bootapps`. 
This property represents a map of environments, which in turn represent a map of hosts each, which in turn represent a 
list of apps each.

The map entry keys for environments and hosts represent the name of the respective environment and host that will be 
displayed on the dashboard. Each app is configured via the following properties.

| Property  | Mandatory | Description |
| ------------- | ------------- | ------------- |
| id | yes | The ID of the app, that is used to identify the app on the dashboard. Must be unique per host. |
| health-endpoint-url | yes | The URL to the health actuator endpoint of the app. |
| health-endpoint-username | no | The username for the health actuator endpoint, if it is secured by basic-auth.  |
| health-endpoint-password | no | The password for the health actuator endpoint, if it is secured by basic-auth. |
| info-endpoint-url | yes | The URL to the info actuator endpoint of the app. |
| info-endpoint-username | no | The username for the info actuator endpoint, if it is secured by basic-auth. |
| info-endpoint-password | no | The password for the info actuator endpoint, if it is secured by basic-auth. |

Example:
```yaml
dashboard:
  bootapps:
    dev:
      host1:
      - id: demo-app-1
        health-endpoint-url: http://localhost:8081/actuator/health
        info-endpoint-url: http://localhost:8081/actuator/info
      host2:
      - id: demo-app-2
        health-endpoint-url: http://localhost:8081/actuator/health
        info-endpoint-url: http://localhost:8081/actuator/info
    prod:
      host1:
      - id: demo-app-3
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