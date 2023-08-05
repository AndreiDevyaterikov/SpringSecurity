package security.services;

import security.entities.RoleEntity;

import java.util.List;

public interface RoleService {
    RoleEntity getRoleByName(String roleName);
}
