package de.htwg.cadworkoutservice.model;

import java.time.LocalDate;
import java.util.List;

public class Workout {
    private Long id;
    private LocalDate date;
    private List<Exercise> exercises;
}
