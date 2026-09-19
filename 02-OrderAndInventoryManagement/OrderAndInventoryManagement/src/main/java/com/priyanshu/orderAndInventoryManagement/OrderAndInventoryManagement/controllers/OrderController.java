package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.controllers;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.*;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.security.JwtUtil;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId() {
        Long userId = JwtUtil.getUserId();
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<OrderPaginatedResponse> getOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrders(pageable));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        Long userId = JwtUtil.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId, orderCreateRequest));
    }

    @PatchMapping("/orders/cancel/{orderId}")
    public ResponseEntity<OrderUpdateResponse> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }


    @PatchMapping("/admin/orders")
    public ResponseEntity<OrderUpdateResponse> updateOrderStatus(@RequestBody OrderUpdateRequest orderUpdateRequest){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderUpdateRequest));
    }

    @DeleteMapping("/admin/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
