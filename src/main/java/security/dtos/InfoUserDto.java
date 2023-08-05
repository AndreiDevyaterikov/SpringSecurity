package security.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InfoUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
