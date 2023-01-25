package de.htwg.cadworkoutservice.service.impl;

import de.htwg.cadworkoutservice.model.WorkoutPlan;
import de.htwg.cadworkoutservice.repository.IWorkoutPlanRepository;
import de.htwg.cadworkoutservice.service.IWorkoutPlanService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutPlanServiceImpl implements IWorkoutPlanService {
    private static final Log LOGGER = LogFactory.getLog(WorkoutPlanServiceImpl.class);

    private final IWorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanServiceImpl(@Autowired IWorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Override
    public WorkoutPlan saveWorkoutPlan(WorkoutPlan workoutPlan) {
        return workoutPlanRepository.save(workoutPlan);
    }

    @Override
    public WorkoutPlan getWorkoutPlanByUserId(String userId) {
        return workoutPlanRepository.getByUserId(userId);
    }

    @Override
    public List<WorkoutPlan> getAll() {
        return workoutPlanRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        workoutPlanRepository.deleteById(id);
    }
}
