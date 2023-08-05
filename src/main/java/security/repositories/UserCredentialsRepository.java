package security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.entities.UserCredentialsEntity;
import security.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Integer> {
    Optional<UserCredentialsEntity> findByUsername(String username);
}
