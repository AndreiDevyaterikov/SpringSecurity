package security.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.dtos.EditUserRolesDto;
import security.dtos.InfoUserDto;
import security.entities.RoleEntity;
import security.entities.UserEntity;
import security.exceptions.SpringSecurityException;
import security.repositories.UserRepository;
import security.response.ResponseModel;
import security.services.RoleService;
import security.services.UserCredentialsService;
import security.services.UserService;
import security.utils.JwtTokenUtils;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserCredentialsService userCredentialsService;
    private final RoleService roleService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userCredentialsOpt = userCredentialsService.getCredentialsByUsername(username);
        var userCredentials = userCredentialsOpt.orElseThrow(() -> notFoundCredentials(username));

        var user = userCredentials.getUser();
        var userRoles = user.getRoles();
        var grantedAuthorities = userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).toList();

        return new User(
                userCredentials.getUsername(),
                userCredentials.getPassword(),
                grantedAuthorities
        );
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public InfoUserDto getUserInfo(String token) {
        var username = jwtTokenUtils.getUsername(token);
        var userCredentialsOpt = userCredentialsService.getCredentialsByUsername(username);
        var userCredentials = userCredentialsOpt.orElseThrow(() -> notFoundCredentials(username));
        var userEntity = userCredentials.getUser();
        var userRoles = userEntity.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .toList();
        return InfoUserDto.builder()
                .username(username)
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .roles(userRoles)
                .build();
    }

    @Override
    public ResponseModel setRolesToUser(EditUserRolesDto editUserRolesDto) {

        var newRoles = editUserRolesDto.getRoles();

        var userEntityOpt = getUserById(editUserRolesDto.getUserId());
        if (userEntityOpt.isPresent()) {
            var userEntity = userEntityOpt.get();
            var userRoles = userEntity.getRoles();
            newRoles.forEach(newRole -> {
                var role = roleService.getRoleByName(newRole);
                userRoles.add(role);
            });
            userEntity.setRoles(userRoles);
            userRepository.save(userEntity);
            return new ResponseModel(HttpStatus.OK, "Roles have been successfully updated");
        } else {
            throw new SpringSecurityException(HttpStatus.NOT_FOUND, "Not found user with id %d");
        }
    }

    @Override
    public Optional<UserEntity> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    private SpringSecurityException notFoundCredentials(String username) {
        var message = String.format("Not found credentials by username: %s", username);
        log.info(message);
        return new SpringSecurityException(
                HttpStatus.NOT_FOUND,
                message
        );
    }
}
