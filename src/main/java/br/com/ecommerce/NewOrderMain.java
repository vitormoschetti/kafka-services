package br.com.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (final var orderDispatcher = new KafkaDispatcher<Order>();
             final var emailDispatcher = new KafkaDispatcher<Email>();) {

            for (int i = 0; i < 15; i++) {

                final var orderId = UUID.randomUUID().toString();
                final var userId = UUID.randomUUID().toString();
                final var amount = new BigDecimal(Math.random() * 5000 + 1);

                final var order = new Order(orderId, userId, amount);

                final var emailSuject = "Thank you for your order! We are processing your order!";

                final Email email = new Email(emailSuject, emailSuject);

                orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);
                emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);

            }

        }
    }
}
