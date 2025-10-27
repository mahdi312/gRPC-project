package com.mst.matt.product.service;

import com.mst.matt.product.proto.ListProductsRequest;
import com.mst.matt.product.proto.ProductRequest;
import com.mst.matt.product.proto.ProductResponse;
import com.mst.matt.product.proto.ProductServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.grpc.Status.NOT_FOUND;

@GrpcService
@Slf4j
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final Map<String, ProductResponse> products = new ConcurrentHashMap<>();

    public ProductGrpcService() {
        products.put("prod1", ProductResponse.newBuilder()
                .setProductId("prod1")
                .setName("Laptop Pro")
                .setDescription("High-performance laptop")
                .setPrice(1200.00)
                .setStockQuantity(50)
                .build());
        products.put("prod2", ProductResponse.newBuilder()
                .setProductId("prod2")
                .setName("Mechanical Keyboard")
                .setDescription("Gaming keyboard with RGB")
                .setPrice(150.00)
                .setStockQuantity(200)
                .build());
        products.put("prod3", ProductResponse.newBuilder()
                .setProductId("prod3")
                .setName("Wireless Mouse")
                .setDescription("Ergonomic wireless mouse")
                .setPrice(75.00)
                .setStockQuantity(300)
                .build());
        products.put("prod4", ProductResponse.newBuilder()
                .setProductId("prod4")
                .setName("Monitor 4K")
                .setDescription("27-inch 4K UHD Monitor")
                .setPrice(450.00)
                .setStockQuantity(80)
                .build());
    }


    @Override
    public void getProductById(ProductRequest request,
                               StreamObserver<ProductResponse> responseObserver) {

        log.info("received getProductById request from productId : {}", request.getProductId());

        ProductResponse productResponse = products.get(request.getProductId());

        if (productResponse != null) {
            responseObserver.onNext(productResponse);
            responseObserver.onCompleted();
        } else {
            log.error("product with id : {} , not found", request.getProductId());
            responseObserver.onError(NOT_FOUND.withDescription("product with id : " + request.getProductId() + " not found").asRuntimeException());
        }
    }

    /**
     *
     */
    @Override
    public void listAllProducts(ListProductsRequest request,
                                StreamObserver<ProductResponse> responseObserver) {


        log.info("listAllProducts request received from category : {} ", request.getCategory());

        products.values().forEach(product -> {
            responseObserver.onNext(product);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("streaming interrupted .. {} ", e.getMessage());
            }
        });

        responseObserver.onCompleted();
        log.info("streaming of ListAllProducts completed...");
    }
}
