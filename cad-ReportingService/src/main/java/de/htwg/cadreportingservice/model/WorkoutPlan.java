package de.htwg.cadreportingservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class WorkoutPlan {
    private Long id;
    private List<Exercise> exercises;
    private UUID userId;
    private UUID creatorId;
}
