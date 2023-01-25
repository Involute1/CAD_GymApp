package de.htwg.cadworkoutservice.repository;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IWorkoutRepository extends JpaRepository<Workout, Long> {
    @Transactional
    @Modifying
    @Query("update Workout w set w.workoutDate = ?1, w.exercises = ?2 where w.id = ?3")
    int updateWorkoutDateAndExercisesById(LocalDate workoutDate, Exercise exercises, Long id);

    List<Workout> getAllByUserId(String userId);

}
