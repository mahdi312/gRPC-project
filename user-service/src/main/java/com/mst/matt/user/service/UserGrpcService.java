package com.mst.matt.user.service;

import com.mst.matt.user.proto.UserRequest;
import com.mst.matt.user.proto.UserResponse;
import com.mst.matt.user.proto.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@GrpcService
@Slf4j
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final Map<String, UserResponse> users = new ConcurrentHashMap<>();

    //in-memory for demonstration
    public UserGrpcService() {
        users.put("user1",
                UserResponse.newBuilder()
                        .setUserId("user1")
                        .setEmail("john.doe@gmail.com")
                        .setUsername("john.doe")
                        .setFirstName("John")
                        .setLastName("Doe")
                        .build()
        );

        users.put("user2", UserResponse.newBuilder()
                .setUserId("user2")
                .setUsername("jane.smith")
                .setEmail("jane.smith@example.com")
                .setFirstName("Jane")
                .setLastName("Smith")
                .build());
    }


    @Override
    public void getUserById(UserRequest request, StreamObserver<UserResponse> responseObs) {
        log.info("getUserById() called for userId: {}", request.getUserId());

        UserResponse response = users.get(request.getUserId());


        if (response != null) {
            responseObs.onNext(response);
            responseObs.onCompleted();
            log.info("sent userResponse for userId : {}", request.getUserId());
        } else {
            responseObs.onError(
                Status.NOT_FOUND
                .withDescription("user with userId : " + request.getUserId() + " not found")
                .asRuntimeException());
            log.warn("user with userId : {} , not found", request.getUserId());
        }





    }
}
