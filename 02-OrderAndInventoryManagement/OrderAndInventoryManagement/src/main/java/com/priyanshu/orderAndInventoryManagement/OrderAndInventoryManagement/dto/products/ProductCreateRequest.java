package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductCreateRequest(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank @Min(1)
        double price,

        @NotBlank @Min(1)
        Integer quantity
) {

}
