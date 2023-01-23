package de.htwg.cadworkoutservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "EXERCISE")
@NoArgsConstructor
@Getter
@Setter
public class Exercise implements Serializable {
    // TODO: Day?
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Set.class, mappedBy = "id", cascade = CascadeType.ALL)
    private List<Set> sets;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Tag.class, mappedBy = "id")
    private List<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Workout.class)
    @JoinColumn(name = "WORKOUT_ID", nullable = false)
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, targetEntity = WorkoutPlan.class)
    @JoinColumn(name = "WORKOUT_PLAN_FKEY")
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
