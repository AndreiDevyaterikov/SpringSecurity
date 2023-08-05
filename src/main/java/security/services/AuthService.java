package security.services;

import security.dtos.JwtRequestDto;
import security.dtos.JwtResponseDto;

public interface AuthService {
    JwtResponseDto authenticate(JwtRequestDto jwtRequestDto);
}
