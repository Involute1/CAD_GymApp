package de.htwg.cadreportingservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Set implements Serializable {
    private Long id;

    private Double weight;
    private int repetition;
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
