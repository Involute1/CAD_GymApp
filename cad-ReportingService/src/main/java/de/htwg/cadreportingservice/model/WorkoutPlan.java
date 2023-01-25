package de.htwg.cadreportingservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class WorkoutPlan {
    private Long id;
    private List<Exercise> exercises;
    private String userId;
    private String creatorId;
}
