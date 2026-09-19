package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull @Min(1)
        BigDecimal price,

        @NotNull @Min(1)
        Integer quantity
) {

}
