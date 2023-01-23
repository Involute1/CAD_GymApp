package de.htwg.caduserservice.repository;

import de.htwg.caduserservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findByUid(String uid);
}
