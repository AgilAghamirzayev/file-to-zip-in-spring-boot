package com.example.demotask.controller;

import com.example.demotask.dto.RequestFilePath;
import com.example.demotask.service.ZipFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ZipFileController.class)
class ZipFileControllerTest {

    @MockBean
    ZipFileService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Mockito.when(service.zipFile("src/main/resources/static/test")).thenReturn(1);
    }

    @Test
    void zipFile() throws Exception {
        RequestFilePath requestFilePath = new RequestFilePath();
        requestFilePath.setPath("src/main/resources/static/test");
        String requestFilePathJson = objectMapper.writeValueAsString(requestFilePath);

        mockMvc.perform(post("/zip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestFilePathJson))
                .andExpect(status().isOk());
    }

    @Test
    void getStatusById() throws Exception {
        mockMvc.perform(get("/status?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}