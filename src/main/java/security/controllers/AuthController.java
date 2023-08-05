package security.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.dtos.InfoUserDto;
import security.dtos.JwtRequestDto;
import security.dtos.JwtResponseDto;
import security.dtos.NewUserDto;
import security.services.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public JwtResponseDto createToken(@RequestBody JwtRequestDto jwtRequestDto) {
        return authService.authenticate(jwtRequestDto);
    }

    @PostMapping("/registrate")
    public InfoUserDto registrate(@RequestBody NewUserDto newUserDto) {
        return authService.registrate(newUserDto);
    }
}
