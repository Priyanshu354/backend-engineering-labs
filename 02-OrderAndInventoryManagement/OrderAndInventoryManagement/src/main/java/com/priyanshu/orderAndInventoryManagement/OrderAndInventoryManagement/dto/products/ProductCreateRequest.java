package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank @Min(1)
        BigDecimal price,

        @NotBlank @Min(1)
        Integer quantity
) {

}
