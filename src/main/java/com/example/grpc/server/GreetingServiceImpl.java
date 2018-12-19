package com.example.grpc.server;

import com.example.Greeting;
import com.example.GreetingServiceGrpc;
import com.example.Hello;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void hello(Hello request, StreamObserver<Greeting> responseObserver) {
        responseObserver.onNext(Greeting.newBuilder().setGreeting("Hello " + request.getFirstname() + " - " + request.getLastname()).build());

        responseObserver.onCompleted();
    }
}
