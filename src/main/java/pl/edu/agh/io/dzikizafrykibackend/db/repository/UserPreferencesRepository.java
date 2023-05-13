package pl.edu.agh.io.dzikizafrykibackend.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.io.dzikizafrykibackend.db.entity.UserPreferencesEntity;

import java.util.UUID;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, UUID> {
}
