package com.microservices.ProductService.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String productName;
    private Long productId;
    private Long quantity;
    private Long price;
}
