package security.dtos;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}
