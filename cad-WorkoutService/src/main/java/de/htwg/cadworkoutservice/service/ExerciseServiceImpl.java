package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.model.Set;
import de.htwg.cadworkoutservice.model.Tag;
import de.htwg.cadworkoutservice.model.Workout;
import de.htwg.cadworkoutservice.repository.IExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements IExerciseService {

    private final IExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(@Autowired IExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise getTagById(long id) {
        return exerciseRepository.getReferenceById(id);
    }

    @Override
    public List<Exercise> getAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    public int updateById(long id, String name, Set sets, Tag tags, Workout workout) {
        return exerciseRepository.updateNameAndSetsAndTagsAndWorkoutById(name, sets, tags, workout, id);
    }
}
