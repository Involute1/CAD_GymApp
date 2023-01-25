package de.htwg.cadreportingservice.model.request;


import de.htwg.cadreportingservice.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutForUserResponse {
    private LocalDate workoutDate;

    private List<Exercise> exercises;

    private String uid;

    private String email;
}
