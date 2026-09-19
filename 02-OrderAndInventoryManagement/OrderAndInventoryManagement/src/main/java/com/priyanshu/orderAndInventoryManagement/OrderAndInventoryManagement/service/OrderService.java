package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart.CartResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.*;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Cart;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.CartItem;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Order;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.OrderItem;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.OrderStatus;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.BadRequestException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.ResourceNotFoundException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.OrderMapper;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.CartItemRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.CartRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.OrderRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;
    private final UserRepo userRepo;
    private final CartService cartService;
    private final OrderItemService orderItemService;

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepo.findAllByUserId(userId);

        return orderMapper.toListOfOrderResponse(orders);
    }

    @PreAuthorize("hasAuthority('ORDER_READ_ALL')")
    public OrderPaginatedResponse getOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepo.findAll(pageable);

        List<OrderResponse> orders = orderMapper.toListOfOrderResponse(orderPage.getContent());

        return new OrderPaginatedResponse(
                orders,
                orderPage.getNumber(),
                orderPage.getSize(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.hasNext()
        );
    }

    @Transactional
    public OrderResponse createOrder(Long userId, OrderCreateRequest orderCreateRequest) {
        List<CartResponse> cartItems = cartService.getCart(userId);

        if(cartItems.isEmpty()){
            throw new BadRequestException("cart is empty");
        }

        List<OrderItem> orderItems = orderMapper.ListOfCartItemsToListOfOrderItems(cartItems);

        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getProductPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(userRepo.getReferenceById(userId));
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(orderCreateRequest.shippingAddress());
        order.setStatus(OrderStatus.PLACED);

        orderRepo.save(order);

        orderItems.forEach((item) -> item.setOrder(order));

        orderItemService.saveAllOrderItems(orderItems);

        return new OrderResponse(order.getId(), order.getCreated_at(),  order.getTotalAmount(), order.getStatus());
    }

    public OrderUpdateResponse cancelOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        if(order.getStatus() == OrderStatus.PLACED || order.getStatus() == OrderStatus.CONFIRMED) {
            order.setStatus(OrderStatus.CANCELLED);
        }
        else{
            throw new BadRequestException("Order can't be canceled");
        }
        return new OrderUpdateResponse(order.getId(), order.getStatus());

    }

    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public OrderUpdateResponse updateOrderStatus(OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepo.findById(orderUpdateRequest.orderId()).orElseThrow(() -> new ResourceNotFoundException("Order", orderUpdateRequest.orderId()));

        if(order.getStatus() == OrderStatus.PLACED || order.getStatus() == OrderStatus.CONFIRMED) {
            order.setStatus(orderUpdateRequest.orderStatus());
        }
        else{
            throw new BadRequestException("Order can't be canceled");
        }
        return new OrderUpdateResponse(order.getId(), order.getStatus());
    }

    @PreAuthorize("hasAuthority('ORDER_DELETE')")
    public void deleteOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        orderRepo.delete(order);
        return ;
    }
}
