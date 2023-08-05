package security.services;

import security.entities.UserCredentialsEntity;

public interface UserCredentialsService {
    UserCredentialsEntity getCredentialsByUsername(String username);
}
