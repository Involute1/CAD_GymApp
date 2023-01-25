package de.htwg.cadreportingservice.model.request;


import de.htwg.cadreportingservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutForUsersRequest {
    private Long workoutDate;
    private List<User> users;
}
