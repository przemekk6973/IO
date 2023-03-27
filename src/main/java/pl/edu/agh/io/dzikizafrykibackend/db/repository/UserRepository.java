package pl.edu.agh.io.dzikizafrykibackend.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
