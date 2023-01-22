package de.htwg.cadreportingservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class Exercise implements Serializable {
    private Long id;
    private String name;
    private List<Set> sets;
    private List<Tag> tags;

    private Workout workout;

    private WorkoutPlan workoutPlan;

    public Exercise(String name, List<Set> sets, List<Tag> tags) {
        this.name = name;
        this.sets = sets;
        this.tags = tags;
    }

    public Exercise(Long id, String name, List<Set> sets, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.sets = sets;
        this.tags = tags;
    }
}
