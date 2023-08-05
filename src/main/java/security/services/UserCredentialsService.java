package security.services;

import security.entities.UserCredentialsEntity;
import security.entities.UserEntity;

public interface UserCredentialsService {
    UserCredentialsEntity getCredentialsByUser(UserEntity userEntity);
    UserCredentialsEntity getCredentialsByUsername(String username);
}
