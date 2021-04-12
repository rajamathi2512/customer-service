package com.customer.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class ApiErrorResponse {
    int statusCode;
    LocalDateTime timestamp;
    String message;
    String description;
    List<String> errors;
}
