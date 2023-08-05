package security.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class JwtResponseDto {
    private String token;
    private String username;
    private Date issued;
    private Date expired;
}
