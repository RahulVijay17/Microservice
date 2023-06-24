package com.microservices.ProductService.service.impl;

import com.microservices.ProductService.entity.Product;
import com.microservices.ProductService.exception.ProductServiceCustomException;
import com.microservices.ProductService.model.ProductRequest;
import com.microservices.ProductService.model.ProductResponse;
import com.microservices.ProductService.repository.ProductRepository;
import com.microservices.ProductService.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("adding product..");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("product created");

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("get the product for productId: {}",productId);
        Product product = productRepository.findById(productId).
                orElseThrow(()->new ProductServiceCustomException("Product not found with id ","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(Long productId, Long quantity) {
        log.info("Reduce quantity {} for id: {}",quantity,productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product id not found","PRODUCT_NOT_FOUND"));
        if (product.getQuantity()<quantity){
            throw new ProductServiceCustomException("product does not have sufficient quantity",
                    "INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product quantity updated successfully");

    }
}
