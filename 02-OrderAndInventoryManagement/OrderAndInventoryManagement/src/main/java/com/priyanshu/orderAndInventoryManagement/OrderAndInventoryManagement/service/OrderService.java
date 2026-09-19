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
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.InventoryMapper;
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
    private final CartItemService cartItemService;
    private final CartRepo cartRepo;
    private final OrderItemService orderItemService;
    private final InventoryService inventoryService;

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
        Cart cart = cartRepo.findByUserId(userId);
        List<CartResponse> cartItems = cartItemService.getCartItems(cart.getId());

        if(cartItems.isEmpty()){
            throw new BadRequestException("cart is empty");
        }

        List<OrderItem> orderItems = orderMapper.ListOfCartItemsToListOfOrderItems(cartItems);

        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getProductPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderItems.forEach((orderItem ->
                inventoryService.decreaseStock(orderItem.getProductId(), orderItem.getQuantity())));

        Order order = new Order();
        order.setUser(userRepo.getReferenceById(userId));
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(orderCreateRequest.shippingAddress());
        order.setStatus(OrderStatus.PLACED);

        orderRepo.save(order);
        orderItems.forEach((item) -> item.setOrder(order));
        orderItemService.saveAllOrderItems(orderItems);

        cartItemService.deleteAllCartItems(cart.getId());

        return new OrderResponse(order.getId(), order.getCreated_at(),  order.getTotalAmount(), order.getStatus());
    }

    @PreAuthorize("@security.canCancelOrder(#orderId)")
    @Transactional
    public OrderUpdateResponse cancelOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order", orderId));

        if (!order.getStatus().isCancellable()) {
            throw new BadRequestException(
                    "Order cannot be cancelled from status "
                            + order.getStatus()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        return new OrderUpdateResponse(
                order.getId(),
                order.getStatus()
        );
    }

    @Transactional
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    public OrderUpdateResponse updateOrderStatus(
            OrderUpdateRequest request) {

        Order order = orderRepo.findById(request.orderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order",
                                request.orderId()
                        ));

        OrderStatus currentStatus = order.getStatus();
        OrderStatus newStatus = request.orderStatus();

        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new BadRequestException(
                    "Cannot change order status from "
                            + currentStatus
                            + " to "
                            + newStatus
            );
        }

        order.setStatus(newStatus);

        return new OrderUpdateResponse(
                order.getId(),
                order.getStatus()
        );
    }

    @PreAuthorize("hasAuthority('ORDER_DELETE')")
    public void deleteOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        orderRepo.delete(order);
        return ;
    }
}
