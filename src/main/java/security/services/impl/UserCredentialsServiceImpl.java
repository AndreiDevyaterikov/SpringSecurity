package security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import security.entities.UserCredentialsEntity;
import security.repositories.UserCredentialsRepository;
import security.services.UserCredentialsService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public Optional<UserCredentialsEntity> getCredentialsByUsername(String username) {
        return userCredentialsRepository.findByUsername(username);
    }

    @Override
    public void saveUserCredentials(UserCredentialsEntity userCredentialsEntity) {
        userCredentialsRepository.save(userCredentialsEntity);
    }
}
