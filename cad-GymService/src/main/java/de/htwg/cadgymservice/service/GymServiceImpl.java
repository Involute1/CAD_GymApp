package de.htwg.cadgymservice.service;

import de.htwg.cadgymservice.model.Gym;
import de.htwg.cadgymservice.repository.IGymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GymServiceImpl implements IGymService {
    private final IGymRepository gymRepository;

    public GymServiceImpl(@Autowired IGymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    @Override
    public Gym saveGym(Gym gym) {
        return gymRepository.save(gym).block();
    }

    @PostConstruct
    public void test() {
        gymRepository.save(new Gym("a", "a", "a")).block();
        System.out.println("done");
    }
}
