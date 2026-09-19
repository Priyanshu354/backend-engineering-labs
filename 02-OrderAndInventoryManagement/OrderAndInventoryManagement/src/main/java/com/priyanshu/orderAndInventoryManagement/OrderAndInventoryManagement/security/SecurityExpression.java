package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.security;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Order;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.OrderRepo;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
public class SecurityExpression {

    private final OrderRepo orderRepo;

    public boolean canCancelOrder(Long orderId) {
        Long userId = JwtUtil.getUserId();
        return orderRepo.findByUserIdAndOrderId(userId, orderId)
                .isPresent();
    }

}
