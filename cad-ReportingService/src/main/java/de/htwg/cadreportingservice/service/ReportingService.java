package de.htwg.cadreportingservice.service;

import com.google.gson.Gson;
import de.htwg.cadreportingservice.model.Gym;
import de.htwg.cadreportingservice.model.User;
import de.htwg.cadreportingservice.model.Workout;
import de.htwg.cadreportingservice.model.request.WorkoutForUsersRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ReportingService {

    private static final Log LOGGER = LogFactory.getLog(ReportingService.class);
    private static final String DEFAULT_GYM_SERVICE_URL = "http://localhost:7081";
    private static final String DEFAULT_USER_SERVICE_URL = "http://localhost:7082";
    private static final String DEFAULT_WORKOUT_SERVICE_URL = "http://localhost:7083";
    private JavaMailSender mailSender;

    public ReportingService(@Autowired JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "* 5 * * * ?")
    public void sendWorkoutsToUsers() throws IOException, InterruptedException {
        LOGGER.info("Starting cronjob");
        Gson gson = new Gson();
        Gym[] gyms = getAllGyms(gson);
        List<User> users = getAllUser(gyms, gson);
        Map<User, Workout> userWorkoutMap = getAllUsersWithWorkoutFromYesterday(users, gson);
        //TODO send emails
        //https://www.baeldung.com/spring-email
    }

    private Gym[] getAllGyms(Gson gson) throws IOException, InterruptedException {
        String gymServiceUrl = System.getenv("GYM_SERVICE_URL");
        if (gymServiceUrl == null) {
            gymServiceUrl = DEFAULT_GYM_SERVICE_URL;
        }
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

        return gson.fromJson(gymResponse.body(), Gym[].class);
    }

    private List<User> getAllUser(Gym[] gyms, Gson gson) {
        String userServiceUrl = System.getenv("USER_SERVICE_URL");
        if (userServiceUrl == null) {
            userServiceUrl = DEFAULT_USER_SERVICE_URL;
        }
        LOGGER.debug("User Service URL: " + userServiceUrl);

        LOGGER.info("Requesting users for gym");
        HttpClient httpClient = HttpClient.newBuilder().build();
        String finalUserServiceUrl = userServiceUrl;
        List<User[]> allUsers = Arrays.stream(gyms).map(gym -> {
            HttpRequest usersRequest = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .GET()
                    .uri(URI.create(finalUserServiceUrl + "/all/" + gym.getTenantId()))
                    .build();
            HttpResponse<String> usersResponse;
            try {
                usersResponse = httpClient.send(usersRequest, HttpResponse.BodyHandlers.ofString());
                LOGGER.info("Unmarshalling response");
                return gson.fromJson(usersResponse.body(), User[].class);
            } catch (IOException | InterruptedException e) {
                LOGGER.error(e.getMessage());
                return new User[0];
            }
        }).toList();

        return allUsers.stream()
                .filter(users -> users.length == 0)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .toList();
    }

    private Map<User, Workout> getAllUsersWithWorkoutFromYesterday(List<User> users, Gson gson) throws IOException, InterruptedException {
        String workoutServiceUrl = System.getenv("WORKOUT_SERVICE_URL");
        if (workoutServiceUrl == null) {
            workoutServiceUrl = DEFAULT_WORKOUT_SERVICE_URL;
        }
        LOGGER.debug("Workout Service URL: " + workoutServiceUrl);

        LOGGER.info("Requesting Workouts");
        WorkoutForUsersRequest workoutForUsersRequest = new WorkoutForUsersRequest(LocalDate.now().minusDays(1L), users);
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest gymsRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(workoutForUsersRequest)))
                .uri(URI.create(workoutServiceUrl + "/users/date"))
                .build();

        HttpResponse<String> gymResponse = httpClient.send(gymsRequest, HttpResponse.BodyHandlers.ofString());
        LOGGER.info("Unmarshalling response");

        return gson.fromJson(gymResponse.body(), Map.class);
    }
}
