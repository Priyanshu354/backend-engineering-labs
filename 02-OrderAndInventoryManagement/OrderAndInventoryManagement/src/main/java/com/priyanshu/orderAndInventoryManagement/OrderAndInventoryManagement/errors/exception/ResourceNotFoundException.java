package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final Long resourceId;

    public ResourceNotFoundException(String resourceName, Long resourceId) {
        super(resourceName + " with id " + resourceId + " not found");
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }
}