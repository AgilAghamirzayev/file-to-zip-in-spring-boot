package com.example.demotask.dto;

import com.example.demotask.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseFilePath {
    private Status status;
    private String filePath;
}
