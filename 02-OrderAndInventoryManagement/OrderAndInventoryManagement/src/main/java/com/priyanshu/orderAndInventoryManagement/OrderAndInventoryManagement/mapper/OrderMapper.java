package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart.CartResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.OrderItemResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.OrderResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Order;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "placedDate", source = "created_at")
    List<OrderResponse> toListOfOrderResponse(List<Order> orders);

    List<OrderItem> ListOfCartItemsToListOfOrderItems(List<CartResponse> cartItems);

    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);
}
