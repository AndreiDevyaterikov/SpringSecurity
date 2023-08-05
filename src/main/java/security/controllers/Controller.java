package security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.dtos.EditUserRolesDto;
import security.dtos.InfoUserDto;
import security.response.ResponseModel;
import security.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Controller {

    private final UserService userService;

    @PostMapping("/myInfo")
    public InfoUserDto test(HttpServletRequest request) {
        return userService.getUserInfo(
                request
                        .getHeader("Authorization")
                        .substring(7)
        );
    }

    @PostMapping("/addRolesToUser")
    public ResponseModel admin(EditUserRolesDto editUserRolesDto) {
        return userService.setRolesToUser(editUserRolesDto);
    }
}
