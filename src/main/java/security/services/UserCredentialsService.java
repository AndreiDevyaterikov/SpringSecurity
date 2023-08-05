package security.services;

import security.entities.UserCredentialsEntity;

import java.util.Optional;

public interface UserCredentialsService {
    Optional<UserCredentialsEntity> getCredentialsByUsername(String username);
    void saveUserCredentials(UserCredentialsEntity userCredentialsEntity);
}
