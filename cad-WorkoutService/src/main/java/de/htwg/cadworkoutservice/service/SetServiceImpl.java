package de.htwg.cadworkoutservice.service;

import de.htwg.cadworkoutservice.model.Set;
import de.htwg.cadworkoutservice.repository.ISetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetServiceImpl implements ISetService {
    private final ISetRepository setRepository;

    public SetServiceImpl(@Autowired ISetRepository setRepository) {
        this.setRepository = setRepository;
    }

    @Override
    public Set saveSet(Set set) {
        return setRepository.save(set);
    }

    @Override
    public Set getTagById(long id) {
        return setRepository.getReferenceById(id);
    }

    @Override
    public List<Set> getAll() {
        return setRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        setRepository.deleteById(id);
    }

    @Override
    public int updateById(long id, Double weight, int repetition) {
        return setRepository.updateWeightAndRepetitionById(weight, repetition, id);
    }
}
