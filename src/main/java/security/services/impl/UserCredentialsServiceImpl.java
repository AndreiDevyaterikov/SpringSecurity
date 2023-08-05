package security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import security.entities.UserCredentialsEntity;
import security.exceptions.SpringSecurityException;
import security.repositories.UserCredentialsRepository;
import security.services.UserCredentialsService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserCredentialsEntity getCredentialsByUsername(String username) {
        var userCredentialsOpt = userCredentialsRepository.findByUsername(username);
        return userCredentialsOpt.orElseThrow(() -> {
            var message = String.format("Not found credentials by username: %s", username);
            log.info(message);
            return new SpringSecurityException(
                    HttpStatus.NOT_FOUND,
                    message
            );
        });
    }
}
