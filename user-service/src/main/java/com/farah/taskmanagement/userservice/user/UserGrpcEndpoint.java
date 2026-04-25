package com.farah.taskmanagement.userservice.user;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class UserGrpcEndpoint extends UserGrpcServiceGrpc.UserGrpcServiceImplBase {

    private final UserRepository userRepository;

    public UserGrpcEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUserById(UserByIdRequest request, StreamObserver<UserReply> responseObserver) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> Status.NOT_FOUND
                        .withDescription("User not found: " + request.getId())
                        .asRuntimeException());

        responseObserver.onNext(UserReply.newBuilder()
                .setId(user.getId())
                .setFullName(user.getFullName())
                .setEmail(user.getEmail())
                .setRole(user.getRole())
                .setActive(user.isActive())
                .build());
        responseObserver.onCompleted();
    }
}
