package security.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SecurityNotFoundException extends RuntimeException {
    private String message;
}
