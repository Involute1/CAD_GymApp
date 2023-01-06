package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Set;

import java.util.List;

public interface ISetService {
    Set saveSet(Set set);

    Set getTagById(long id);

    List<Set> getAll();

    void deleteById(long id);

    int updateById(long id, Double weight, int repetition);
}
