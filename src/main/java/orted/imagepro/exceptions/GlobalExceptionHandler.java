package orted.imagepro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<GlobalExceptionResponse> resourceNotFound(InvalidFileTypeException ex) {
        GlobalExceptionResponse response = new GlobalExceptionResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());

        return new ResponseEntity<GlobalExceptionResponse>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
