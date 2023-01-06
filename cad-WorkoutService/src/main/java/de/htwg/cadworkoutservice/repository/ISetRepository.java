package de.htwg.cadworkoutservice.repository;

import de.htwg.cadworkoutservice.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ISetRepository extends JpaRepository<Set, Long> {
    @Transactional
    @Modifying
    @Query("update Set s set s.weight = ?1, s.repetition = ?2 where s.id = ?3")
    int updateWeightAndRepetitionById(Double weight, int repetition, Long id);
}
