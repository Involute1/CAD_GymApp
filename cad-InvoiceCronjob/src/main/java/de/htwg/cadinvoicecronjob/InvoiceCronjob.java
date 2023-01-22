package de.htwg.cadinvoicecronjob;

import com.google.gson.Gson;
import de.htwg.cadinvoicecronjob.model.Gym;
import de.htwg.cadinvoicecronjob.model.Invoice;
import de.htwg.cadinvoicecronjob.model.User;
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
    private static final double COST_PER_USER = 0.3;
    private static final String DEFAULT_GYM_SERVICE_URL = "http://localhost:7081";
    private static final String DEFAULT_USER_SERVICE_URL = "http://localhost:7082";

    public static void main(String[] args) throws IOException, InterruptedException {
        LOGGER.info("Starting Invoice Generation");

        String gymServiceUrl = System.getenv("GYM_SERVICE_URL");
        if (gymServiceUrl == null) {
            gymServiceUrl = DEFAULT_GYM_SERVICE_URL;
        }
        LOGGER.debug("Gym Service URL: " + gymServiceUrl);

        String userServiceUrl = System.getenv("USER_SERVICE_URL");
        if (userServiceUrl == null) {
            userServiceUrl = DEFAULT_USER_SERVICE_URL;
        }
        LOGGER.debug("User Service URL: " + userServiceUrl);

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
        String finalUserServiceUrl = userServiceUrl;
        List<Invoice> invoices = Arrays.stream(gyms).map(gym -> {
            double amount = switch (gym.getBillingModel()) {
                case FREE:
                    yield 0;
                case STANDARD:
                    yield 100;
                case ENTERPRISE: {
                    LOGGER.info("Requesting users for gym");
                    HttpRequest usersRequest = HttpRequest.newBuilder()
                            .header("Content-Type", "application/json")
                            .GET()
                            .uri(URI.create(finalUserServiceUrl + "/all/" + gym.getTenantId()))
                            .build();
                    HttpResponse<String> usersResponse;
                    try {
                        usersResponse = httpClient.send(usersRequest, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    LOGGER.info("Unmarshalling response");
                    User[] users = gson.fromJson(usersResponse.body(), User[].class);
                    long userCount = Arrays.stream(users).filter(user -> !user.isDisabled()).count();
                    yield userCount * COST_PER_USER;
                }
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
