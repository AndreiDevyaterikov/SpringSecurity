package security.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import security.response.ResponseModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CustomDinedFailureHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getOutputStream().println(getMessage(accessDeniedException));
    }

    private String getMessage(AccessDeniedException accessDeniedException) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        objectMapper.setDateFormat(df);

        var message = "Not enough rights - " + accessDeniedException.getMessage();
        var response = new ResponseModel(HttpStatus.FORBIDDEN, message);

        return objectMapper.writeValueAsString(response);
    }
}
