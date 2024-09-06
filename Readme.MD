# Feature Switches Spring

## Introduction
This project is a simple implementation of feature switches in Spring Boot. It is a simple way to enable or disable features in your application without changing the code.

## How to use
To use this project, you need to add the following dependency to your `pom.xml` file:

### Add gradle/maven dependency

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.rahulnehru:feature-switches-spring:1.0.0'
}
```

### Create your feature switches in your application.conf file

```hocon
switches.default {

  switchA = true
  switchB = false
  multilevel {
    switchC = "2020-01-01T00:00:00.000Z"
    switchD = "false"
  }

}
```

### Access your feature switches in your code

```java
import org.springframework.beans.factory.annotation.Autowired;

public class AppClass {

    @Autowired
    private FeatureSwitches featureSwitches;

    public void someMethod() {
        if (featureSwitches.isOn("switchA")) {
            // Do something
        }
    }
}
```

### Toggle your boolean feature switches in test environments using the REST API 

To toggle your feature switches in test environments, you can use the REST API provided by this project. To enable the REST API, you need to add the following property to your `application.properties` file:

```properties
spring.profiles.active=toggling
```

You can then use the following endpoints to toggle your feature switches:
```http request
GET /switch/status/context/{{context}}

GET /switch/status/context/{{context}}/{{switchName}}

PUT /switch/context/{{context}}/{switchName}/toggle

PUT /switch/context/{{context}}/{switchName}/reset

GET /time-travel/now

PUT /time-travel/set -d { "time": "2020-01-01T00:00:00.000Z", "running": true }

DELETE /time-travel/reset
```

#### Notes
- `{{context}}` is the context of your feature switches. By default, the context is `default`.
- `{{switchName}}` is the name of your feature switch. For multi-level feature switches, you can use the dot notation to access the nested feature switches. For example, `multilevel.switchC`.
- The `time-travel` endpoints are used to manipulate the current time in the application. This is useful when you want to test time-sensitive feature switches.
- You can use the `running` property in the `time-travel/set` endpoint to start or stop the clock during time travelling. For example, to set the clock at '2020-01-01T00:00:00.000Z' and stop the clock, you can use the following request body: `{ "time": "2020-01-01T00:00:00.000Z", "running": false }




