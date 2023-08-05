package security.services;

import security.entities.RoleEntity;

public interface RoleService {
    RoleEntity getRoleByName(String roleName);
}
