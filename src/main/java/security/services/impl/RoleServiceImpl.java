package security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import security.entities.RoleEntity;
import security.exceptions.SpringSecurityException;
import security.repositories.RoleRepository;
import security.services.RoleService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity getRoleByName(String roleName) {
        var roleEntityOpt = roleRepository.findByName(roleName);
        return roleEntityOpt.orElseThrow(() -> {
            var message = String.format("Not found role with name %s", roleName);
            log.warn(message);
            return new SpringSecurityException(HttpStatus.NOT_FOUND, message);

        });
    }
}
