package security.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import security.dtos.InfoUserDto;
import security.dtos.JwtRequestDto;
import security.dtos.JwtResponseDto;
import security.dtos.NewUserDto;
import security.entities.UserCredentialsEntity;
import security.entities.UserEntity;
import security.exceptions.SpringSecurityException;
import security.services.AuthService;
import security.services.RoleService;
import security.services.UserCredentialsService;
import security.utils.JwtTokenUtils;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserCredentialsService userCredentialsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


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

    @Override
    public InfoUserDto registrate(NewUserDto newUserDto) {

        var username = newUserDto.getUsername();
        var userCredentialsOpt = userCredentialsService.getCredentialsByUsername(username);

        if (userCredentialsOpt.isPresent()) {
            var message = String.format("User with %s username already exists", username);
            log.info(message);
            throw new SpringSecurityException(HttpStatus.CONFLICT, message);
        } else {
            if (newUserDto.getPassword().equals(newUserDto.getConfirmPassword())) {

                var userRoleEntity = roleService.getRoleByName("USER");

                var newUserEntity = UserEntity.builder()
                        .firstName(newUserDto.getFirstname())
                        .lastName(newUserDto.getLastname())
                        .roles(
                                List.of(
                                        userRoleEntity
                                )
                        )
                        .build();
                userService.saveUser(newUserEntity);

                var userCredentialsEntity = UserCredentialsEntity.builder()
                        .user(newUserEntity)
                        .password(passwordEncoder.encode(newUserDto.getPassword()))
                        .username(newUserDto.getUsername())
                        .build();
                userCredentialsService.saveUserCredentials(userCredentialsEntity);

                return InfoUserDto.builder()
                        .firstName(newUserDto.getFirstname())
                        .lastName(newUserDto.getLastname())
                        .username(newUserDto.getUsername())
                        .build();
            } else {
                throw new SpringSecurityException(HttpStatus.CONFLICT, "Passwords differences");
            }
        }
    }
}
