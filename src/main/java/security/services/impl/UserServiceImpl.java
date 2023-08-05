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
import security.entities.UserEntity;
import security.exceptions.SpringSecurityException;
import security.repositories.UserRepository;
import security.services.UserCredentialsService;
import security.services.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserCredentialsService userCredentialsService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userCredentialsOpt = userCredentialsService.getCredentialsByUsername(username);
        var userCredentials = userCredentialsOpt.orElseThrow(() -> {
            var message = String.format("Not found credentials by username: %s", username);
            log.info(message);
            return new SpringSecurityException(
                    HttpStatus.NOT_FOUND,
                    message
            );
        });

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
}
