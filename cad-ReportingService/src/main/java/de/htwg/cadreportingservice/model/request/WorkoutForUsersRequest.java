package de.htwg.cadreportingservice.model.request;


import de.htwg.cadreportingservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutForUsersRequest {
    private LocalDate workoutDate;
    private List<User> users;
}
