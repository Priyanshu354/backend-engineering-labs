package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.controllers;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.OrderCreateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.OrderResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.OrderUpdateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order.OrderUpdateResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.security.JwtUtil;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderCreateRequest));
    }

    @PatchMapping("/orders/orderItem/cancel/{orderItemId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderItemId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderItemId));
    }


    @PatchMapping("/admin/orders")
    public ResponseEntity<OrderUpdateResponse> updateOrderStatus(@RequestBody OrderUpdateRequest orderUpdateRequest){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderUpdateRequest));
    }

    @DeleteMapping("/admin/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }

}
