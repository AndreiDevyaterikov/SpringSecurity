package security.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.dtos.JwtRequestDto;
import security.dtos.JwtResponseDto;
import security.services.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public JwtResponseDto createToken(@RequestBody JwtRequestDto jwtRequestDto) {
        return authService.authenticate(jwtRequestDto);
    }
}
