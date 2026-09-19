package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order;

import java.util.List;

public record OrderPaginatedResponse(
        List<OrderResponse> orders,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {}
