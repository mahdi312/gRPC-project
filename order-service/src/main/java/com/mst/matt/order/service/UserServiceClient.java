package com.mst.matt.order.service;

import com.mst.matt.user.proto.UserRequest;
import com.mst.matt.user.proto.UserResponse;
import com.mst.matt.user.proto.UserServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceClient {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public UserResponse getUserDetails(String userId) {
        log.info("getUserDetails with calling userService --> userId : {}", userId);

        try {
            UserRequest userRequest = UserRequest.newBuilder().setUserId(userId).build();
            return userServiceBlockingStub.getUserById(userRequest);
        } catch (Exception e) {
            log.error("error while fetching user through calling userService.getUserById --> userId : {} and errorMessage : {}", userId, e.getMessage());
            throw new RuntimeException("failed through fetching user : " + e.getMessage(), e);
        }
    }


}
