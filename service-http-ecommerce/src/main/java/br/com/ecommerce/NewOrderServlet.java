package br.com.ecommerce;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try (final var orderDispatcher = new KafkaDispatcher<Order>();
             final var emailDispatcher = new KafkaDispatcher<Email>();) {

            final var email = Math.random() + "@gmail.com";


            final var orderId = UUID.randomUUID().toString();
            final var amount = new BigDecimal(Math.random() * 5000 + 1);

            final var order = new Order(orderId, amount, email);

            final var emailSubject = "Thank you for your order! We are processing your order!";

            final Email emailCode = new Email(email, emailSubject);

            orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order);
            emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode);

        } catch (InterruptedException e) {
            throw new ServletException(e);
        } catch (ExecutionException e) {
            throw new ServletException(e);
        }
    }
}
