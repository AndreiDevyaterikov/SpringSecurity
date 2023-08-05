package security.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.dtos.UserDto;
import security.entities.RoleEntity;
import security.repositories.UserRepository;
import security.services.UserCredentialsService;
import security.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserCredentialsService userCredentialsService;

    @Override
    public UserDto getUserByLastAndFirstName(String lastName, String firstName) {
        var optUserEntity = userRepository.findByFirstNameAndLastName(firstName, lastName);

        return null;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userCredentials = userCredentialsService.getCredentialsByUsername(username);
        var user = userCredentials.getUser();
        var userRoles = user.getRoles();
        var userRolesString = userRoles.stream().map(RoleEntity::getName).toList();
        var grantedAuthorities = userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).toList();

//        return User.builder()
//                .username(userCredentials.getUsername())
//                .password(userCredentials.getPassword())
//                .roles(String.valueOf(userRolesString))
//                .build();

        return new User(
                userCredentials.getUsername(),
                userCredentials.getPassword(),
                grantedAuthorities
        );
    }
}
