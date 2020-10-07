package br.com.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String orderId, userId;
    private final BigDecimal amount;
    private String email;

    public Order(String orderId, String userId, BigDecimal amount, String email) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", amount=" + amount +
                ", email='" + email + '\'' +
                '}';
    }
}
