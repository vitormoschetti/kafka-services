package br.com.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String orderId, userId;
    private final BigDecimal amount;

    public Order(String orderId, String userId, BigDecimal amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
