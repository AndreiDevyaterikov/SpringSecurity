package security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import security.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

}
