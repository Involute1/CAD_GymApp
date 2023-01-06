package de.htwg.cadworkoutservice.repository;

import de.htwg.cadworkoutservice.model.Exercise;
import de.htwg.cadworkoutservice.model.Set;
import de.htwg.cadworkoutservice.model.Tag;
import de.htwg.cadworkoutservice.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IExerciseRepository extends JpaRepository<Exercise, Long> {
    @Transactional
    @Modifying
    @Query("update Exercise e set e.name = ?1, e.sets = ?2, e.tags = ?3, e.workout = ?4 where e.id = ?5")
    int updateNameAndSetsAndTagsAndWorkoutById(String name, Set sets, Tag tags, Workout workout, Long id);

}
