package br.com.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService {

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

    public static void main(String[] args) {
        final var fraudDetectorService = new FraudDetectorService();
        final var kafkaService =
                new KafkaService<Order>(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER", fraudDetectorService::parse, Order.class, Map.of());
        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
        System.out.println("----------------------------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            //ignore
            e.printStackTrace();
        }

        final var order = record.value();

        if(order.getAmount().compareTo(new BigDecimal("4500")) >=0){
            System.out.println("Order is a fraund!!!" + order);
            orderDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getUserId(), order);
        }else{
            System.out.println("Approved: " + order);
            orderDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getUserId(), order);
        }

    }

}
