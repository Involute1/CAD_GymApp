package de.htwg.cadworkoutservice.controller;

import de.htwg.cadworkoutservice.model.User;
import de.htwg.cadworkoutservice.model.Workout;
import de.htwg.cadworkoutservice.model.request.WorkoutForUsersRequest;
import de.htwg.cadworkoutservice.service.impl.WorkoutServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{userId}")
    public List<Workout> getWorkoutsByUserId(@PathVariable String userId) {
        //TODO var checking
        //TODO logging
        return Hibernate.unproxy(workoutService.getAllWorkoutByUserId(userId), List.class);
    }

    @PostMapping("/users/date")
    public Map<User, Workout> getWorkoutForUsersForDate(@RequestBody WorkoutForUsersRequest workoutForUsersRequest) {
        return workoutService.getUserWithWorkoutOnDate(workoutForUsersRequest);
    }

    @GetMapping("/")
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

    @PatchMapping("/{workoutId}")
    public void updateById(@PathVariable long workoutId) {
        //TODO var checking
        //TODO logging
        workoutService.updateWorkout(workoutId, null, null);
    }
}
