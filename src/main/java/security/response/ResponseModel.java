package security.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ResponseModel {

    private Integer httpCode;
    private HttpStatus httpStatus;
    private String message;
    private Date timestamp;

    public ResponseModel(HttpStatus httpStatus, String message) {
        this.httpCode = httpStatus.value();
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = new Date();
    }
}
