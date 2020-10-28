package br.com.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (final var orderDispatcher = new KafkaDispatcher<Order>();
             final var emailDispatcher = new KafkaDispatcher<Email>();) {

            final var email = Math.random() + "@gmail.com";

            for (int i = 0; i < 15; i++) {

                final var orderId = UUID.randomUUID().toString();
                final var amount = new BigDecimal(Math.random() * 5000 + 1);

                final var order = new Order(orderId, amount, email);

                final var emailSubject = "Thank you for your order! We are processing your order!";

                final Email emailCode = new Email(email, emailSubject);

                orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order);
                emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode);

            }

        }
    }
}
