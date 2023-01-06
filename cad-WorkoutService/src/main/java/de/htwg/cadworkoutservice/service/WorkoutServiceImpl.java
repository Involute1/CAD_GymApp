package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.model.Workout;
import de.htwg.cadworkoutservice.repository.IWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutServiceImpl implements IWorkoutService {
    private final IWorkoutRepository workoutRepository;

    public WorkoutServiceImpl(@Autowired IWorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public Workout getWorkoutById(long id) {
        return workoutRepository.getReferenceById(id);
    }

    @Override
    public int updateWorkout(long id, LocalDate workoutDate, Exercise exercise) {
        return workoutRepository.updateWorkoutDateAndExercisesById(workoutDate, exercise, id);
    }

    @Override
    public List<Workout> getAll() {
        return workoutRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        workoutRepository.deleteById(id);
    }
}
