package de.htwg.cadworkoutservice.model.request;

import de.htwg.cadworkoutservice.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutForUsersRequest {
    private LocalDate workoutDate;
    private List<User> users;
}
