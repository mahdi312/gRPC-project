package com.mst.matt.order.service;

import com.mst.matt.order.proto.OrderRequest;
import com.mst.matt.order.proto.OrderResponse;
import com.mst.matt.order.proto.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OrderGrpcService extends OrderServiceGrpc.OrderServiceImplBase {


    @Override
    public void createOrder(OrderRequest request, StreamObserver<OrderResponse> orderObs) {

        //create and save orderRequest in db, we just skip this in the project for simplicity.

    }


}
