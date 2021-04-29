package com.example.demotask.utility;

import com.example.demotask.enums.Status;
import com.example.demotask.exception.CustomFileNotFoundException;
import com.example.demotask.exception.CustomFileAlreadyExistsException;
import com.example.demotask.exception.CustomInvalidPathException;
import com.example.demotask.model.FileInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Getter
@Setter
@Log4j2
@Component
public class ZipUtility {
    private static final String ZIP_FILE_PATH = "src/main/resources/zip-files/";
    private List<FileInfo> idAndPaths = new ArrayList<>();

    public FileInfo zip(final String dirPath) {
        FileInfo fileInfo = new FileInfo();
        String zipFilePath = String.format("%s%s.zip", ZIP_FILE_PATH, Paths.get(dirPath).getFileName());
        log.info("Compressing to {}", zipFilePath);
        isFileExists(zipFilePath);

        Path sourceDirPath = Paths.get(dirPath);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));
             Stream<Path> paths = Files.walk(sourceDirPath)) {
            fileInfo.setStatus(Status.IN_PROGRESS);
            paths
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(path, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            fileInfo.setStatus(Status.FAILED);
                            throw new CustomFileAlreadyExistsException(zipFilePath);
                        }
                    });
        } catch (FileNotFoundException e) {
            fileInfo.setStatus(Status.FAILED);
            throw new CustomFileNotFoundException(sourceDirPath, idAndPaths.size() + 1);
        } catch (IOException e) {
            fileInfo.setStatus(Status.FAILED);
            throw new CustomInvalidPathException(sourceDirPath, idAndPaths.size() + 1);
        }

        fileInfo.setId(idAndPaths.size() + 1);
        fileInfo.setPath(zipFilePath);
        setStatusToCompleted(fileInfo);
        idAndPaths.add(fileInfo);
        log.info("Zip is created at : " + zipFilePath);

        return fileInfo;
    }

    private void isFileExists(String zipFilePath) {
        try {
            Files.createFile(Paths.get(zipFilePath));
        } catch (IOException e) {
            throw new CustomFileAlreadyExistsException(zipFilePath);
        }
    }

    private void setStatusToCompleted(FileInfo fileInfo) {
        if (fileInfo.getStatus() != null && !fileInfo.getStatus().equals(Status.FAILED)) {
            fileInfo.setStatus(Status.COMPLETED);
        }
    }
}
