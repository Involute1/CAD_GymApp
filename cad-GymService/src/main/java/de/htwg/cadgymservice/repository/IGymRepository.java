package de.htwg.cadgymservice.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import de.htwg.cadgymservice.model.Gym;
import org.springframework.stereotype.Repository;

@Repository
public interface IGymRepository extends FirestoreReactiveRepository<Gym> {
}
