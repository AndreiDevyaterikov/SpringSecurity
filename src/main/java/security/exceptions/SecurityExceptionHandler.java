package security.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(SecurityNotFoundException.class)
    public ResponseModel notFoundException(SecurityNotFoundException exception, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseModel(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }
}
