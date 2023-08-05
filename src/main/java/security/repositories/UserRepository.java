package security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <UserEntity, Integer> {
    Optional<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
