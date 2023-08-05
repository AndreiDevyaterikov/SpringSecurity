package security.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import security.response.ResponseModel;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(SpringSecurityException.class)
    public ResponseModel securityExceptionHandler(SpringSecurityException exception, HttpServletResponse response) {
        response.setStatus(exception.getHttpStatus().value());
        return new ResponseModel(
                exception.getHttpStatus(),
                exception.getMessage()
        );
    }
}
