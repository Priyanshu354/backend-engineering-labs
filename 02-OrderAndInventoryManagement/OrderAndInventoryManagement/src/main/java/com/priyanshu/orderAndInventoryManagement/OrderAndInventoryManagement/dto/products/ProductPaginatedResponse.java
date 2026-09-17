package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products;

import java.util.List;

public record ProductPaginatedResponse(
        List<ProductResponse> products,
        Long cursorKey
) {
}
