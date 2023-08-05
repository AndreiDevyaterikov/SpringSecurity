package security.dtos;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class NewUserDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String confirmPassword;
}
