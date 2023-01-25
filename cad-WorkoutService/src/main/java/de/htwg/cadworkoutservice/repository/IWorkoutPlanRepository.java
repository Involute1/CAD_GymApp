package de.htwg.cadworkoutservice.repository;

import de.htwg.cadworkoutservice.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    WorkoutPlan getByUserId(String userId);
}
