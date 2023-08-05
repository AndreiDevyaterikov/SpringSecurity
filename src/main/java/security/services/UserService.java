package security.services;

import security.dtos.UserDto;

public interface UserService {
    UserDto getUserByLastAndFirstName(String lastName, String firstName);
}
