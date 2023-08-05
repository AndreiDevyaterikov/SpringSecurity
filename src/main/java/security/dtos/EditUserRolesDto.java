package security.dtos;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class EditUserRolesDto {
    private Integer userId;
    private List<String> roles;
}
