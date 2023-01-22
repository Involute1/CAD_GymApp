package de.htwg.cadworkoutservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "WORKOUT_PLAN")
@NoArgsConstructor
@Getter
@Setter
public class WorkoutPlan {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "EXERCISES")
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Exercise.class, mappedBy = "id")
    private List<Exercise> exercises;

    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "CREATOR_ID")
    private UUID creatorId;

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
