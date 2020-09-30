package br.com.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudDetectorService {

    public static void main(String[] args) {
        final var fraudDetectorService = new FraudDetectorService();
        final var kafkaService =
                new KafkaService<Order>(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER", fraudDetectorService::parse, Order.class, Map.of());
        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("----------------------------------------");
        System.out.println("Processing new order, checking for fraud");
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
