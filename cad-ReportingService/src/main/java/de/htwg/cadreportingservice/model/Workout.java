package de.htwg.cadreportingservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class Workout implements Serializable {
    private Long id;
    private LocalDate workoutDate;

    private UUID userId;

    private List<Exercise> exercises;

    public Workout(Long id, LocalDate workoutDate, List<Exercise> exercises) {
        this.id = id;
        this.workoutDate = workoutDate;
        this.exercises = exercises;
    }

    public Workout(LocalDate workoutDate, List<Exercise> exercises) {
        this.workoutDate = workoutDate;
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "workoutDate=" + workoutDate +
                ", exercises=" + exercises +
                '}';
    }
}
