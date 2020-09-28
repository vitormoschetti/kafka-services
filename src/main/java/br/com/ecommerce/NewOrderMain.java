package br.com.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (final var dispatcher = new KafkaDispatcher()) {

            for (int i = 0; i < 15; i++) {

                final var key = UUID.randomUUID().toString();
                final var value = key + ",321,123";
                final var email = "Thank you for your order! We are processing your order!";

                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);

            }

        }
    }
}
