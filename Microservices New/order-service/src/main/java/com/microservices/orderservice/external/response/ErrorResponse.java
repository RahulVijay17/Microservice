package com.microservices.orderservice.external.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String errorMessage;
    private String errorCode;
}
