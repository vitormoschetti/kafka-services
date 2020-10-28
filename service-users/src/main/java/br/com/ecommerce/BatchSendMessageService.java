package br.com.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BatchSendMessageService {

    private final Connection connection;
    private final KafkaDispatcher<User> userDispatcher = new KafkaDispatcher<>();

    BatchSendMessageService() throws SQLException {
        String url = "jdbc:sqlite:target/users_database.db";
        connection = DriverManager.getConnection(url);
        try {
            connection.createStatement().execute("create table Users (" +
                    "uuid varchar(200) primary key," +
                    "email varchar(200))");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws SQLException {
        var batchService = new BatchSendMessageService();
        try (var service = new KafkaService<String>(BatchSendMessageService.class.getSimpleName(),
                "SEND_MESSAGE_TO_ALL_USERS",
                batchService::parse,
                String.class,
                Map.of())) {
            service.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse(ConsumerRecord<String, String> record) throws ExecutionException, InterruptedException, SQLException {
        System.out.println("------------------------------------------");
        System.out.println("Processing new batch for topic " + record.value());

        for(User user : getAllUsers()){
            userDispatcher.send(record.value(), user.getUuid(), user);
        }
    }

    private List<User> getAllUsers() throws SQLException {

        final var results = connection.prepareStatement("SELECT uuid FROM USERS").executeQuery();
        List<User> users = new ArrayList<>();
        while(results.next()){
            users.add(new User(results.getString(1)));
        }

        return users;

    }

}
