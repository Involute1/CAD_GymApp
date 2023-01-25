package de.htwg.cadgymservice.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import de.htwg.cadgymservice.model.FirestoreGym;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IGymRepository extends FirestoreReactiveRepository<FirestoreGym> {

    Mono<FirestoreGym> findByTenantId(String tenantId);
}
