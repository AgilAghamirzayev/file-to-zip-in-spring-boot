package com.example.demotask.model;

import com.example.demotask.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileInfo {
    private Integer id;
    private String path;
    private Status status;
}
