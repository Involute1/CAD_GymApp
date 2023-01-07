package de.htwg.cadgymservice.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import de.htwg.cadgymservice.model.FirebaseGym;
import org.springframework.stereotype.Repository;

@Repository
public interface IGymRepository extends FirestoreReactiveRepository<FirebaseGym> {
}
