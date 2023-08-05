package security.services;

import security.dtos.InfoUserDto;
import security.dtos.JwtRequestDto;
import security.dtos.JwtResponseDto;
import security.dtos.NewUserDto;

public interface AuthService {
    JwtResponseDto authenticate(JwtRequestDto jwtRequestDto);

    InfoUserDto registrate(NewUserDto newUserDto);
}
