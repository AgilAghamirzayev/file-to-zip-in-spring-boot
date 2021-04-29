package com.example.demotask.exception.handling;

import com.example.demotask.exception.CustomFileAlreadyExistsException;
import com.example.demotask.exception.CustomFileNotFoundException;
import com.example.demotask.exception.CustomInvalidPathException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@Log4j2
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String EXCUSE = "Something went wrong. Please contact aqilzeka99@gmail.com!";

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        Optional<String> optionalErrorMessage = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).findFirst();
        String errorMessage = EXCUSE;
        if (optionalErrorMessage.isPresent()) {
            errorMessage = optionalErrorMessage.get();
        }
        log.error("Validation error:", e);
        return new ResponseEntity<>(new RequestError(InternalErrorCode.INVALID_VALUE, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomInvalidPathException.class)
    public ResponseEntity<RequestError> customInvalidPathException(CustomInvalidPathException e) {
        log.error("Your path is invalid please input a valid path : {} (Look at README.md file)", e.getMessage());
        return new ResponseEntity<>(new RequestError(InternalErrorCode.INVALID_PATH_EXCEPTION, e.getMessage()), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(CustomFileNotFoundException.class)
    public ResponseEntity<RequestError> customFileNotFoundException(CustomFileNotFoundException e) {
        log.error("File not found: {}", e.getMessage());
        return new ResponseEntity<>(new RequestError(InternalErrorCode.FILE_NOT_FOUND_EXCEPTION, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomFileAlreadyExistsException.class)
    public ResponseEntity<RequestError> customFileAlreadyExistsException(CustomFileAlreadyExistsException e) {
        log.error("File already exists : {}", e.getMessage());
        return new ResponseEntity<>(new RequestError(InternalErrorCode.FILE_ALREADY_EXISTS_EXCEPTION, e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
