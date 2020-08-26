# SWITCH
Provides online constant change capability

## Solution
use zookeeper to store scope data, so we need at least two application:
- zookeeper: single (suggest zookeeper cluster) 
- switch-server

switch-server and switch-client use http sync
## How to use
first of all:
add `@EnableSwitchAutoConfiguration` Under the SpringBootApplication;
like this:
```java
@SpringBootApplication
@EnableSwitchAutoConfiguration
public class SwitchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwitchApplication.class, args);
    }
}
```

Then define a constant class like this:
```java
/**
 * <br/>
 * since 2020/8/25
 *
 * @author eddie.lys
 */
@Switch(appName = "test", serverAddr = "http://localhost:9999")
public class TestConstant {

    @SwitchConstant(desc = "测试", security = Level.L3)
    public static final Boolean TEST = true;
}
```

As you can see, there has two Switch annotation

1. @Switch(appName = "test", serverAddr = "http://localhost:9999")
    - Statement this is a constant class of Switch management.
    - use space `test` .
    - server address is `http://localhost:9999`.
2. @SwitchConstant(desc = "test-only", security = Level.L3)
    - Statement this is a constant field of Switch management.
    - field description is `test-only`.
    - security field declare the effect of update this field, Level.L3 is the highest Level.
 
## WARN
UN-FINISHED:
switch-server only for test, cannot use
    
