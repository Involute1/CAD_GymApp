package de.htwg.cadreportingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Exercise implements Serializable {
    private Long id;
    private String name;

    private int sets;

    private int repetition;

    private int weight;

    private String tag;
}
