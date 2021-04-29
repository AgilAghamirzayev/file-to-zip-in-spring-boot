package com.example.demotask.service;

import com.example.demotask.dto.ResponseFilePath;

public interface ZipFileService {
    Integer zipFile(String path);
    ResponseFilePath getStatusById(Integer id);
}
