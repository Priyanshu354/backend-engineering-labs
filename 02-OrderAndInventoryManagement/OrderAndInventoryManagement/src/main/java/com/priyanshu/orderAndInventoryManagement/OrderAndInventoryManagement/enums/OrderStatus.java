package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums;

public enum OrderStatus {

    PLACED,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus newStatus) {

        return switch (this) {

            case PLACED ->
                    newStatus == CONFIRMED ||
                            newStatus == CANCELLED;

            case CONFIRMED ->
                    newStatus == SHIPPED ||
                            newStatus == CANCELLED;

            case SHIPPED ->
                    newStatus == DELIVERED;

            case DELIVERED, CANCELLED ->
                    false;
        };
    }

    public boolean isCancellable() {
        return this == PLACED || this == CONFIRMED;
    }
}