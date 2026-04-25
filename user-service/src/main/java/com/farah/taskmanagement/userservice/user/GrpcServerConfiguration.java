package com.farah.taskmanagement.userservice.user;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
public class GrpcServerConfiguration {

    private final Server server;

    public GrpcServerConfiguration(
            @Value("${grpc.server.port}") int grpcPort,
            List<BindableService> grpcServices
    ) throws IOException {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(grpcPort);
        grpcServices.forEach(serverBuilder::addService);
        this.server = serverBuilder.build();
        this.server.start();
    }

    @PreDestroy
    public void shutdown() {
        server.shutdown();
    }
}
