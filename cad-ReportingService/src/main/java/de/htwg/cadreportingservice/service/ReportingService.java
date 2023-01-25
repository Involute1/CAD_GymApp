package de.htwg.cadreportingservice.service;

import com.google.gson.Gson;
import de.htwg.cadreportingservice.model.Exercise;
import de.htwg.cadreportingservice.model.Gym;
import de.htwg.cadreportingservice.model.User;
import de.htwg.cadreportingservice.model.request.WorkoutForUserResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportingService {

    private static final Log LOGGER = LogFactory.getLog(ReportingService.class);
    private static final String DEFAULT_GYM_SERVICE_URL = "http://localhost:7081/gym/";
    private static final String DEFAULT_USER_SERVICE_URL = "http://localhost:7082/user/";
    private static final String DEFAULT_WORKOUT_SERVICE_URL = "http://localhost:7083/workout/";
    private JavaMailSender mailSender;

    @Scheduled(cron = "* 5 * * * ?")
    public void sendWorkoutsToUsers() throws IOException, InterruptedException {
        LOGGER.info("Starting cronjob");
        Gson gson = new Gson();
        Gym[] gyms = getAllGyms(gson);
        List<User> users = getAllUser(gyms, gson);
        sendMails(getAllUsersWithWorkoutFromYesterday(users));
    }

    public void sendWorkoutToUser(String uid, String tenantId) throws IOException, InterruptedException {
        LOGGER.info("Starting cronjob");
        User user = getOwnUser(uid, tenantId);
        sendMails(getAllUsersWithWorkoutFromYesterday(List.of(user)));
    }

    private void sendMails(List<WorkoutForUserResponse> userWorkoutMap) {
        userWorkoutMap.forEach((value) -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@gymapp.com");
            message.setTo(value.getEmail());
            message.setSubject("Report");
            message.setText(generateReport(value));
            mailSender.send(message);
        });
    }

    private String generateReport(WorkoutForUserResponse workout) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Report for ");
        stringBuilder.append(workout.getWorkoutDate());
        stringBuilder.append("\r\n In total you completed ");
        stringBuilder.append(workout.getExercises().size());
        stringBuilder.append(" yesterday: \r\n");
        workout.getExercises().forEach(exercise -> {
            stringBuilder.append(exercise.getName())
                    .append(":")
                    .append(exercise.getSets())
                    .append(" Sets of ")
                    .append(exercise.getRepetition())
                    .append(" repetitions with ")
                    .append(exercise.getWeight())
                    .append(" kg;\r\n");
        });
        return stringBuilder.toString();
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

    private User getOwnUser(String uid, String tenantId) {
        String userServiceUrl = System.getenv("USER_SERVICE_URL");
        if (userServiceUrl == null) {
            userServiceUrl = DEFAULT_USER_SERVICE_URL;
        }
        LOGGER.debug("User Service URL: " + userServiceUrl);
        LOGGER.info("Requesting user");
        return WebClient.create(userServiceUrl + uid + "/" + tenantId)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();
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

    private List<WorkoutForUserResponse> getAllUsersWithWorkoutFromYesterday(List<User> users) throws IOException, InterruptedException {
        String workoutServiceUrl = System.getenv("WORKOUT_SERVICE_URL");
        if (workoutServiceUrl == null) {
            workoutServiceUrl = DEFAULT_WORKOUT_SERVICE_URL;
        }


        LOGGER.debug("Workout Service URL: " + workoutServiceUrl);

        LOGGER.info("Requesting Workouts");
        LOGGER.info("Unmarshalling response");
        ParameterizedTypeReference<List<WorkoutForUserResponse>> typeRef = new ParameterizedTypeReference<>() {
        };

        return List.of(new WorkoutForUserResponse(LocalDate.now(), List.of(new Exercise(1L, "asd", 1, 2, 3, "asd")), users.get(0).getUid(), users.get(0).getEmail()));
        /*return WebClient.create(workoutServiceUrl + "users/date")
                .post()
                .bodyValue(new WorkoutForUsersRequest(LocalDateTime.now().minusDays(1L).toEpochSecond(ZoneOffset.UTC), users))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(typeRef)
                .block();*/
    }
}
