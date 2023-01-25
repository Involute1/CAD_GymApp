package de.htwg.cadworkoutservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "EXERCISE")
@NoArgsConstructor
@Getter
@Setter
public class Exercise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int sets;

    private int repetition;

    private int weight;

    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY)
    private WorkoutPlan workoutPlan;

    public Exercise(String name, int sets, int repetition, int weight) {
        this.name = name;
        this.sets = sets;
        this.repetition = repetition;
        this.weight = weight;
        this.tag = tag;
    }
}
