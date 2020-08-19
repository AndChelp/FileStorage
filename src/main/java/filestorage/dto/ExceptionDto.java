package filestorage.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
public class ExceptionDto {
    private Timestamp time;
    private int status;
    private String type;
    private String message;

    public ExceptionDto(String message, HttpStatus httpStatus) {
        time = new Timestamp(System.currentTimeMillis());
        status = httpStatus.value();
        type = httpStatus.name();
        this.message = message;
    }

}
