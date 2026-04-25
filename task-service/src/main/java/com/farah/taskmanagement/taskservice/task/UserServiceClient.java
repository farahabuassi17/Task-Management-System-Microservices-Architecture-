package com.farah.taskmanagement.taskservice.task;

import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClient {

    private final UserGrpcServiceGrpc.UserGrpcServiceBlockingStub userGrpcClient;

    public UserServiceClient(ManagedChannel userServiceChannel) {
        this.userGrpcClient = UserGrpcServiceGrpc.newBlockingStub(userServiceChannel);
    }

    public UserSummary getActiveUser(String userId) {
        try {
            UserReply user = userGrpcClient.getUserById(UserByIdRequest.newBuilder()
                    .setId(userId)
                    .build());

            if (!user.getActive()) {
                throw new IllegalArgumentException("Assigned user is inactive: " + userId);
            }

            return new UserSummary(
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole(),
                    user.getActive()
            );
        } catch (StatusRuntimeException ex) {
            if (ex.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new IllegalArgumentException("Assigned user was not found: " + userId);
            }

            throw new IllegalStateException("User service gRPC request failed: " + ex.getStatus(), ex);
        }
    }
}
