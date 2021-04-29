package com.example.demotask.exception.handling;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InternalErrorCode {

    INVALID_PATH_EXCEPTION(100, "invalid_path_exception"),
    FILE_NOT_FOUND_EXCEPTION(101, "file_not_found_exception"),
    FILE_ALREADY_EXISTS_EXCEPTION(102, "file_already_exists_exception"),
    INVALID_VALUE(108, "invalid_value");

    private final int id;
    private final String code;
}