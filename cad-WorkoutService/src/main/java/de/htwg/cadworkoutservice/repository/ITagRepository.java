package de.htwg.cadworkoutservice.repository;

import de.htwg.cadworkoutservice.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ITagRepository extends JpaRepository<Tag, Long> {
    @Transactional
    @Modifying
    @Query("update Tag t set t.name = ?1 where t.id = ?2")
    int updateNameById(String name, Long id);


}
