package security.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.repositories.UserRepository;
import security.services.UserCredentialsService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCredentialsService userCredentialsService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userCredentials = userCredentialsService.getCredentialsByUsername(username);
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
}
