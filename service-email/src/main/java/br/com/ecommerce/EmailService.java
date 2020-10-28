package br.com.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {

    public static void main(String[] args) {

        final EmailService emailService = new EmailService();

        final var kafkaService = new KafkaService<>(EmailService.class.getSimpleName(), "ECOMMERCE_SEND_EMAIL", emailService::parse, Email.class, Map.of());
        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, Email> record) {
        System.out.println("----------------------------------------");
        System.out.println("Send new order");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //ignore
            e.printStackTrace();
        }
    }


}
