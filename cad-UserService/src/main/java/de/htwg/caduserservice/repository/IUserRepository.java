package de.htwg.caduserservice.repository;

import de.htwg.caduserservice.model.GymAppUserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<GymAppUserData, Long> {

    GymAppUserData findByUid(String uid);
}
