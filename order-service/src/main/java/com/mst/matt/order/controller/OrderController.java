package com.mst.matt.order.controller;

import com.mst.matt.order.proto.OrderRequest;
import com.mst.matt.order.proto.OrderResponse;
import com.mst.matt.order.service.UserServiceClient;
import com.mst.matt.user.proto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final UserServiceClient userServiceClient;

    public OrderController(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @GetMapping(value = "/user/{id}")
    public Mono<UserResponse> getUserById(@PathVariable String id) {

        return Mono.fromCallable(() -> userServiceClient.getUserDetails(id))
                .doOnError(e -> log.error("there was an error in calling getUserDetail service : {}", e.getMessage()));

    }

    @PostMapping()
    public Mono<OrderResponse> createOrder(OrderRequest request) {

        //for simplicity just create a sample order and return it...

        return Mono.just(OrderResponse
                .newBuilder()
                .setOrderId("temp-order-123")
                .setUserId(request.getUserId())
                .setTotalAmount(100.0)
                .setOrderStatus("PENDING")
                .setOrderDate(java.time.Instant.now().toString())
                .build());

    }

}
