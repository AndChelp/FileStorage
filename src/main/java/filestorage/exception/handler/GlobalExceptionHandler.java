package filestorage.exception.handler;

import filestorage.dto.ExceptionDto;
import filestorage.exception.FileDataNotFoundException;
import filestorage.exception.FileTypeNotSupportedException;
import filestorage.exception.NullFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = Logger.getAnonymousLogger();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleAllUnhandled(Exception ex) {
        logger.log(Level.WARNING, "Handle internal exception", ex);
        return new ResponseEntity<>(new ExceptionDto("Unexpected exception", HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileDataNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleFileDataNotFound(Exception ex) {
        logger.log(Level.INFO, ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto(ex.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ZipException.class, NullFileException.class})
    public ResponseEntity<ExceptionDto> handleZipException(Exception ex) {
        logger.log(Level.INFO, ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto(ex.getMessage(), HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileTypeNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleFileTypeNotSupported(Exception ex) {
        logger.log(Level.INFO, ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto(ex.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionDto> handleMaxUploadSizeExceededException(Exception ex) {
        logger.log(Level.INFO, ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto("Maximum file size - 15MB", HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(Exception ex) {
        logger.log(Level.INFO, ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto("Incorrect argument", HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }
}