package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.WorkoutPlan;

import java.util.List;

public interface IWorkoutPlanService {
    WorkoutPlan saveWorkoutPlan(WorkoutPlan workoutPlan);

    WorkoutPlan getWorkoutPlanByUserId(String userId);

    List<WorkoutPlan> getAll();

    void deleteById(long id);
}
