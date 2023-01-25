package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.model.User;
import de.htwg.cadworkoutservice.model.Workout;
import de.htwg.cadworkoutservice.model.request.WorkoutForUsersRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IWorkoutService {
    Workout saveWorkout(Workout workout);

    List<Workout> getAllWorkoutByUserId(String userId);

    int updateWorkout(long id, LocalDate workoutDate, Exercise exercises);

    List<Workout> getAll();

    void deleteById(long id);

    Map<User, Workout> getUserWithWorkoutOnDate(WorkoutForUsersRequest workoutForUsersRequest);
}
