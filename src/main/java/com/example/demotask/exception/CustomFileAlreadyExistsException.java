package com.example.demotask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class CustomFileAlreadyExistsException extends  RuntimeException{
    public CustomFileAlreadyExistsException(String entity) {
        super(String.format("%s file already exist", entity));
    }
}