package security.services;

import security.dtos.EditUserRolesDto;
import security.dtos.InfoUserDto;
import security.entities.UserEntity;
import security.response.ResponseModel;

import java.util.Optional;

public interface UserService {
    void saveUser(UserEntity userEntity);
    InfoUserDto getUserInfo(String token);
    ResponseModel setRolesToUser(EditUserRolesDto editUserRolesDto);

    Optional<UserEntity> getUserById(Integer userId);
}
