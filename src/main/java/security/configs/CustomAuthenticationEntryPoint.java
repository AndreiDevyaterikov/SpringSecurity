package security.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import security.response.ResponseModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().println(getMessage());
    }

    private String getMessage() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        objectMapper.setDateFormat(df);

        var message = "Dined access for unauthorized users";
        var response = new ResponseModel(HttpStatus.UNAUTHORIZED, message);

        return objectMapper.writeValueAsString(response);
    }
}
