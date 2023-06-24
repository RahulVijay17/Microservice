package com.microservices.orderservice.service;

import com.microservices.orderservice.model.OrderRequest;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);
}
