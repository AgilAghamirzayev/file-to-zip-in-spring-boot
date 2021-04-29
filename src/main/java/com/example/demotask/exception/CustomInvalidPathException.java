package com.example.demotask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.Path;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomInvalidPathException extends  RuntimeException{
    public CustomInvalidPathException(Path entity, Integer id) {
        super(String.format("Your path %s is invalid %d", entity, id));
    }
}
