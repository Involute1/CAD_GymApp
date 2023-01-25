package de.htwg.cadworkoutservice.service.impl;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.model.User;
import de.htwg.cadworkoutservice.model.Workout;
import de.htwg.cadworkoutservice.model.request.WorkoutForUsersRequest;
import de.htwg.cadworkoutservice.repository.IWorkoutRepository;
import de.htwg.cadworkoutservice.service.IWorkoutService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkoutServiceImpl implements IWorkoutService {
    private static final Log LOGGER = LogFactory.getLog(WorkoutServiceImpl.class);

    private final IWorkoutRepository workoutRepository;

    public WorkoutServiceImpl(@Autowired IWorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public List<Workout> getAllWorkoutByUserId(String userId) {
        return workoutRepository.getAllByUserId(userId);
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

    @Override
    public Map<User, Workout> getUserWithWorkoutOnDate(WorkoutForUsersRequest workoutForUsersRequest) {
        List<Workout> filteredWorkouts = getAll().stream()
                .filter(workout -> workout.getWorkoutDate().equals(workoutForUsersRequest.getWorkoutDate()))
                .toList();

        Map<User, Workout> userWorkoutMap = new HashMap<>();
        for (Workout workout : filteredWorkouts) {
            for (User user : workoutForUsersRequest.getUsers()) {
                if (workout.getUserId().equals(user.getUid())) {
                    userWorkoutMap.put(user, workout);
                    break;
                }
            }
        }

        return userWorkoutMap;
    }
}
