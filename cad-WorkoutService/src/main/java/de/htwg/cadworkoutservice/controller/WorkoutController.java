package de.htwg.cadworkoutservice.controller;

import de.htwg.cadworkoutservice.model.Workout;
import de.htwg.cadworkoutservice.service.WorkoutServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkoutController {
    //TODO testing
    private static final Log LOGGER = LogFactory.getLog(Workout.class);

    private final WorkoutServiceImpl workoutService;

    public WorkoutController(@Autowired WorkoutServiceImpl workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/")
    public Workout insertWorkout(@RequestBody Workout workout) {
        return workoutService.saveWorkout(workout);
    }

    @GetMapping("/{workoutId}")
    public Workout getWorkoutById(@PathVariable long workoutId) {
        //TODO var checking
        //TODO logging
        return workoutService.getWorkoutById(workoutId);
    }

    @GetMapping("/all")
    public List<Workout> getAllWorkouts() {
        //TODO var checking
        //TODO logging
        return workoutService.getAll();
    }

    @DeleteMapping("/{workoutId}")
    public void deleteById(@PathVariable long workoutId) {
        //TODO var checking
        //TODO logging
        workoutService.deleteById(workoutId);
    }

    @PatchMapping("/workoutId")
    public void updateById(@PathVariable long workoutId) {
        //TODO var checking
        //TODO logging
        workoutService.updateWorkout(workoutId, null, null);
    }
}
