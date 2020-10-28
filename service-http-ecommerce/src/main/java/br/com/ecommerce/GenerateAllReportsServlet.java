package br.com.ecommerce;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GenerateAllReportsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (final var batchDispatcher = new KafkaDispatcher<String>()) {

            batchDispatcher.send("SEND_MESSAGE_TO_ALL_USERS", "USER_GENERATE_READING_REPORT", "USER_GENERATE_READING_REPORT");

            System.out.println("Sent generate report to all users!!");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Report request generated");


        } catch (InterruptedException e) {
            throw new ServletException(e);
        } catch (ExecutionException e) {
            throw new ServletException(e);
        }
    }
}
