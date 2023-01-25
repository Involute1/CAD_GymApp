package de.htwg.cadworkoutservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "WORKOUT")
@NoArgsConstructor
@Getter
@Setter
public class Workout implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "WORKOUT_DATE")
    private LocalDate workoutDate;

    @Column(name = "USER_ID")
    private String userId;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
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
}
