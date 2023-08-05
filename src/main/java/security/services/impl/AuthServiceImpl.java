package security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import security.dtos.JwtRequestDto;
import security.dtos.JwtResponseDto;
import security.exceptions.SpringSecurityException;
import security.services.AuthService;
import security.utils.JwtTokenUtils;

import java.time.Duration;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.lifetime}")
    private Duration jwtLifeTime;

    @Override
    public JwtResponseDto authenticate(JwtRequestDto jwtRequestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequestDto.getUsername(),
                            jwtRequestDto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            var message = "Invalid login or password";
            log.warn(message);
            throw new SpringSecurityException(
                    HttpStatus.UNAUTHORIZED,
                    message
            );
        }
        var userDetails = userService.loadUserByUsername(jwtRequestDto.getUsername());
        var token = jwtTokenUtils.generateToken(userDetails);
        var issuedDate = new Date();
        var expiredDate = new Date(issuedDate.getTime() + jwtLifeTime.toMillis());

        return JwtResponseDto.builder()
                .username(jwtRequestDto.getUsername())
                .token(token)
                .issued(issuedDate)
                .expired(expiredDate)
                .build();
    }
}
