package com.example.demotask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.Path;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomFileNotFoundException extends  RuntimeException{
    public CustomFileNotFoundException(Path entity, Integer id) {
        super(String.format("%s not found by id: %d", entity, id));
    }

    public CustomFileNotFoundException(Integer id) {
        super(String.format("File not found by id: %d",  id));
    }
}
