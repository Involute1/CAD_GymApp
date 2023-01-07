package de.htwg.cadworkoutservice.controller;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.service.ExerciseServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    //TODO testing
    private static final Log LOGGER = LogFactory.getLog(ExerciseController.class);

    private final ExerciseServiceImpl exerciseService;

    public ExerciseController(@Autowired ExerciseServiceImpl exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/")
    public Exercise insertExercise(@RequestBody Exercise exercise) {
        //TODO var checks
        //TODO logging

        return exerciseService.saveExercise(exercise);
    }

    @GetMapping("/{exerciseId}")
    public Exercise getExerciseById(@PathVariable long exerciseId) {
        //TODO var checks
        //TODO logging
        return exerciseService.getTagById(exerciseId);
    }

    @GetMapping("/all")
    public List<Exercise> getAllExercises() {
        //TODO var checks
        //TODO logging
        return exerciseService.getAll();
    }

    @DeleteMapping("/{exerciseId}")
    public void deleteById(@PathVariable long exerciseId) {
        //TODO var checks
        //TODO logging
        exerciseService.deleteById(exerciseId);
    }

    @PatchMapping("/exerciseId")
    public void updateById(@PathVariable long exerciseId) {
        //TODO var checks
        //TODO logging
        exerciseService.updateById(exerciseId, "", null, null, null);
    }
}
