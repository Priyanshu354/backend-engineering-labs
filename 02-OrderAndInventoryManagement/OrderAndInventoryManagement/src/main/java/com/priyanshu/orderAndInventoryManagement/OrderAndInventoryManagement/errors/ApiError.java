package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ApiError(
        HttpStatus status,
        String message,
        Instant timestamp
) {
    public ApiError(HttpStatus status, String message) {
        this(status, message, Instant.now());
    }
}
