package br.com.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class ReadingReportService {

     private static Path SORUCE = new File("src/main/resources/report.txt").toPath();

    public static void main(String[] args) {
        final var reportService = new ReadingReportService();
        final var kafkaService =
                new KafkaService<>(ReadingReportService.class.getSimpleName(), "USER_GENERATE_READING_REPORT", reportService::parse, User.class, Map.of());
        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, User> record) throws IOException {
        System.out.println("----------------------------------------");
        System.out.println("Processing report for " + record.value());

        var user = record.value();
        final var target = new File(user.getReportPath());

        IO.copyTo(SORUCE, target);
        IO.append(target, "Create for " + user.getUuid());




    }

}
