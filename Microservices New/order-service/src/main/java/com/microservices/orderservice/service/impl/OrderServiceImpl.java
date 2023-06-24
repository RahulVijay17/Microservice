package com.microservices.orderservice.service.impl;

import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.external.client.ProductService;
import com.microservices.orderservice.model.OrderRequest;
import com.microservices.orderservice.repository.OrderRepository;
import com.microservices.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        log.info("order request: {}",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Creating order with status CREATED");

        Order order = Order.builder().
                amount(orderRequest.getTotalAmount())
                        .orderStatus("CREATED")
                                .productId(orderRequest.getProductId())
                                        .orderDate(Instant.now())
                                                .quantity(orderRequest.getQuantity()).
                                                            build();

       order = orderRepository.save(order);
       log.info("order placed successfully with order id: {} ",order.getOrderId());
        return order.getOrderId();
    }
}
