package de.htwg.cadworkoutservice.controller;

import de.htwg.cadworkoutservice.model.WorkoutPlan;
import de.htwg.cadworkoutservice.service.impl.WorkoutPlanServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/plan")
public class WorkoutPlanController {
    //TODO testing
    private static final Log LOGGER = LogFactory.getLog(WorkoutPlan.class);

    private final WorkoutPlanServiceImpl workoutPlanService;

    public WorkoutPlanController(@Autowired WorkoutPlanServiceImpl workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @PostMapping("/")
    public WorkoutPlan insertWorkoutPlan(@RequestBody WorkoutPlan workoutPlan) {
        return workoutPlanService.saveWorkoutPlan(workoutPlan);
    }

    @GetMapping("/{userId}")
    public WorkoutPlan getWorkoutPlanByUserId(@PathVariable String userId) {
        //TODO var checking
        //TODO logging
        return Hibernate.unproxy(workoutPlanService.getWorkoutPlanByUserId(userId), WorkoutPlan.class);
    }

    @GetMapping("/")
    public List<WorkoutPlan> getAllWorkoutPlans() {
        //TODO var checking
        //TODO logging
        return workoutPlanService.getAll();
    }

    @DeleteMapping("/{workoutPlanId}")
    public void deleteById(@PathVariable long workoutPlanId) {
        //TODO var checking
        //TODO logging
        workoutPlanService.deleteById(workoutPlanId);
    }
}
