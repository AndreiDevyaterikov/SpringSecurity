package security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import security.entities.UserCredentialsEntity;
import security.entities.UserEntity;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Integer> {
    Optional<UserCredentialsEntity> findByUser(UserEntity userEntity);
    Optional<UserCredentialsEntity> findByUsername(String username);
}
