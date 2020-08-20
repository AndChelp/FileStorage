package filestorage.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionDto {
    private long time;
    private int status;
    private String type;
    private String message;

    public ExceptionDto(String message, HttpStatus httpStatus) {
        time = System.currentTimeMillis();
        status = httpStatus.value();
        type = httpStatus.name();
        this.message = message;
    }

}
