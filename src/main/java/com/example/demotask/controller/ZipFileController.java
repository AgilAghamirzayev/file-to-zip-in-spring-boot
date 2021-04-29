package com.example.demotask.controller;

import com.example.demotask.dto.RequestFilePath;
import com.example.demotask.dto.ResponseFilePath;
import com.example.demotask.service.ZipFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ZipFileController {

    private final ZipFileService zipFileService;

    @PostMapping("/zip")
    public ResponseEntity<Integer> zipFile(@RequestBody RequestFilePath path) {
        Integer id = zipFileService.zipFile(path.getPath());

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseFilePath> getStatusById(@RequestParam(name = "id") Integer id) {
        ResponseFilePath statusById = zipFileService.getStatusById(id);

        return new ResponseEntity<>(statusById, HttpStatus.OK);
    }
}
