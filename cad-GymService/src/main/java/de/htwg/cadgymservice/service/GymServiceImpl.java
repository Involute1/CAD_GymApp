package de.htwg.cadgymservice.service;

import de.htwg.cadgymservice.model.FirestoreGym;
import de.htwg.cadgymservice.repository.IGymRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymServiceImpl implements IGymService {
    private static final Log LOGGER = LogFactory.getLog(GymServiceImpl.class);

    private final IGymRepository gymRepository;

    public GymServiceImpl(@Autowired IGymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    @Override
    public FirestoreGym saveGym(FirestoreGym gym) {
        return gymRepository.save(gym).block();
    }

    @Override
    public FirestoreGym getGym(String firebaseId) {
        return gymRepository.findById(firebaseId).block();
    }

    @Override
    public FirestoreGym updateGym(FirestoreGym updatedGym) {
        gymRepository.deleteById(updatedGym.getTenantId()).block();
        return saveGym(updatedGym);
    }

    @Override
    public boolean deleteGym(String firebaseId) {
        gymRepository.deleteById(firebaseId).block();
        return true;
    }

    @Override
    public List<FirestoreGym> getGyms() {
        return gymRepository.findAll().collectList().block();
    }
}
