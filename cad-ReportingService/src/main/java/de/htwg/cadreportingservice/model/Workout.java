package de.htwg.cadreportingservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    private UUID userId;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Exercise.class, mappedBy = "id")
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
