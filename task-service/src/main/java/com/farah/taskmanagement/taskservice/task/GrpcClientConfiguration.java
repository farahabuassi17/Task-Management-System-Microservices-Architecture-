package com.farah.taskmanagement.taskservice.task;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfiguration {

    @Bean(destroyMethod = "shutdownNow")
    public ManagedChannel userServiceChannel(
            @Value("${user.service.grpc.host}") String host,
            @Value("${user.service.grpc.port}") int port
    ) {
        return ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }
}
