package com.example.demotask.service.impl;

import com.example.demotask.dto.ResponseFilePath;
import com.example.demotask.exception.CustomFileNotFoundException;
import com.example.demotask.model.FileInfo;
import com.example.demotask.service.ZipFileService;
import com.example.demotask.utility.ZipUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ZipFileServiceImpl implements ZipFileService{
    private final ZipUtility zipUtility;

    public ZipFileServiceImpl(ZipUtility zipUtility) {
        this.zipUtility = zipUtility;
    }

    @Override
    public Integer zipFile(String path) {
        log.info("Zipping {} file", path);
        Integer id = zipUtility.zip(path).getId();
        log.info("Zipped id's {} file", id);
        return id;
    }

    @Override
    public ResponseFilePath getStatusById(Integer id) {
        log.info("Getting id's {} file", id);
        ResponseFilePath responseFilePath = new ResponseFilePath();

        if (zipUtility.getIdAndPaths().size() <= id || zipUtility.getIdAndPaths().get(id) == null) {
            throw new CustomFileNotFoundException(id);
        } else {
            FileInfo fileInfo = zipUtility.getIdAndPaths().get(id-1);
            log.info("Get {}", fileInfo);

            responseFilePath.setStatus(fileInfo.getStatus());
            responseFilePath.setFilePath(fileInfo.getPath());
        }

        return responseFilePath;
    }

}
