package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.model.Set;
import de.htwg.cadworkoutservice.model.Tag;
import de.htwg.cadworkoutservice.model.Workout;

import java.util.List;

public interface IExerciseService {
    Exercise saveExercise(Exercise exercise);

    Exercise getTagById(long id);

    List<Exercise> getAll();

    void deleteById(long id);

    int updateById(long id, String name, Set sets, Tag tags, Workout workout);
}
