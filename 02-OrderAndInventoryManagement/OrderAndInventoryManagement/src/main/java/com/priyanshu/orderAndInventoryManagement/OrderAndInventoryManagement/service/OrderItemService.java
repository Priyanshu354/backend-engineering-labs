package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.OrderItemResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.OrderItem;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.BadRequestException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.OrderMapper;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.OrderItemRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepo orderItemRepo;
    private final OrderMapper orderMapper;

    public void saveAllOrderItems(List<OrderItem> orderItems) {
        orderItemRepo.saveAll(orderItems);
    }

    public OrderItemResponse getOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId).orElseThrow(() -> new BadRequestException("Order item doesn't exist"));
        return orderMapper.orderItemToOrderItemResponse(orderItem);
    }
}
