package de.htwg.cadinvoicecronjob;

import com.google.gson.Gson;
import de.htwg.cadinvoicecronjob.model.Gym;
import de.htwg.cadinvoicecronjob.model.Invoice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class InvoiceCronjob {
    private static final Log LOGGER = LogFactory.getLog(InvoiceCronjob.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        LOGGER.info("Starting Invoice Generation");

        String gymServiceName = System.getenv("GYM_SERVICE");
        String gymServiceUrl = "http://" + (gymServiceName == null ? "localhost:7081" : gymServiceName);
        LOGGER.debug("Gym Service URL: " + gymServiceUrl);

        LOGGER.info("Requesting gyms");
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest gymsRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .GET()
                .uri(URI.create(gymServiceUrl))
                .build();
        HttpResponse<String> gymResponse = httpClient.send(gymsRequest, HttpResponse.BodyHandlers.ofString());

        LOGGER.info("Unmarshalling response");
        Gson gson = new Gson();
        Gym[] gyms = gson.fromJson(gymResponse.body(), Gym[].class);


        LOGGER.info("Creating invoices");
        List<Invoice> invoices = Arrays.stream(gyms).map(gym -> {
            double amount = switch (gym.getBillingModel()) {
                case FREE: yield 0;
                case STANDARD: yield 100;
                case ENTERPRISE: yield 1000; // TODO: Use user count in calculation
            };
            return new Invoice(null, LocalDateTime.now(), amount, LocalDateTime.now().plusDays(3), gym.getFirebaseId());
        }).toList();
        String invoicesJson = gson.toJson(invoices);

        LOGGER.info("Sending back invoices");
        String invoicesUrl = gymServiceUrl + "/invoices";
        LOGGER.debug("Invoices URL: " + invoicesUrl);
        HttpRequest invoiceRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(invoicesJson))
                .uri(URI.create(invoicesUrl))
                .build();

        LOGGER.info("Done!");
    }
}