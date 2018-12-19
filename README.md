# GRPC Server
This is a simple example to use the gRPC into the Spring-Boot app

- Dependencies of pom
```xml
<dependency>
    <groupId>io.github.lognet</groupId>
    <artifactId>grpc-spring-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>io.github.lognet</groupId>
    <artifactId>grpc-spring-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-protobuf</artifactId>
    <version>1.15.1</version>
</dependency>
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-stub</artifactId>
    <version>1.15.1</version>
</dependency>

```

- Add the build dependencies
```xml
<build>
    <extensions>
        <extension>
            <groupId>kr.motd.maven</groupId>
            <artifactId>os-maven-plugin</artifactId>
            <version>1.5.0.Final</version>
        </extension>
    </extensions>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <plugin>
            <groupId>org.xolstice.maven.plugins</groupId>
            <artifactId>protobuf-maven-plugin</artifactId>
            <version>0.5.1</version>
            <configuration>
                <protocArtifact>com.google.protobuf:protoc:3.5.1-1:exe:${os.detected.classifier}</protocArtifact>
                <pluginId>grpc-java</pluginId>
                <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.17.1:exe:${os.detected.classifier}</pluginArtifact>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                        <goal>compile-custom</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

- Proto file must be into `/src/main/proto`
```
syntax = "proto3";

package com.example;

option java_multiple_files = true;


message Hello {
    string firstname = 1;
    string lastname = 2;
}

message Greeting {
    string greeting = 1;
}

service GreetingService {
    rpc hello(Hello) returns (Greeting);
}
```

- Run `mvn clean package` to create the files of server gRPC base and the request and response  

- The files are created into the folder *target/generated-sources/protobuf* (Make sure your IDE read those files as source)

- Create a server
```java
package com.example.grpc;

import com.example.Greeting;
import com.example.GreetingServiceGrpc;
import com.example.Hello;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void hello(Hello request, StreamObserver<Greeting> responseObserver) {

        responseObserver.onNext(Greeting.newBuilder().setGreeting("Hello " + request.getFirstname() + " " + request.getLastname() ).build());

        responseObserver.onCompleted();
    }
}

```

- Add the properties
```
grpc.port=8092
```

:tada: Done! 

Just running your application `mvn spring-boot:run` or build and run the jar.

You should see into the logs:

```
o.l.springboot.grpc.GRpcServerRunner     : Starting gRPC Server ...
o.l.springboot.grpc.GRpcServerRunner     : 'com.example.grpc.server.GreetingServiceImpl' service has been registered.
o.l.springboot.grpc.GRpcServerRunner     : gRPC Server started, listening on port 8092.
```