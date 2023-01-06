package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.model.Workout;

import java.time.LocalDate;
import java.util.List;

public interface IWorkoutService {
    Workout saveWorkout(Workout workout);

    Workout getWorkoutById(long id);

    int updateWorkout(long id, LocalDate workoutDate, Exercise exercises);

    List<Workout> getAll();

    void deleteById(long id);
}
