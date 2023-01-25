package de.htwg.cadworkoutservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "WORKOUT_PLAN")
@Data
public class WorkoutPlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Exercise> exercises;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CREATOR_ID")
    private String creatorId;
}
