package de.htwg.cadworkoutservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SET")
@NoArgsConstructor
@Getter
@Setter
public class Set implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "WEIGHT")
    private Double weight;
    @Column(name = "REPETITION")
    private int repetition;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Exercise.class)
    @JoinColumn(name = "EXERCISE_ID", nullable = false)
    private Exercise exercise;

    public Set(Long id, Double weight, int repetition) {
        this.id = id;
        this.weight = weight;
        this.repetition = repetition;
    }

    public Set(Double weight, int repetition) {
        this.weight = weight;
        this.repetition = repetition;
    }
}
