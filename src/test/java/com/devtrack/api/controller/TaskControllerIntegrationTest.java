package com.devtrack.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.devtrack.api.dto.TaskCreateRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;


@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

        @Autowired
    private ObjectMapper objectMapper
    ;
    //Test GET (RESTwise)
    @Test
    void shouldReturnTaskById() throws Exception {
        // 1. Δημιουργούμε πρώτα ένα task μέσω POST
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Test Task");
        request.setDescription("Test Description");

        String responseJson = mockMvc.perform(
                post("/api/v1/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        // 2. Παίρνουμε το πραγματικό ID που δημιουργήθηκε
        JsonNode created = objectMapper.readTree(responseJson);
        Long createdId = created.get("id").asLong();

    mockMvc.perform(
            get("/api/v1/tasks/" + createdId)
    )
        .andExpect(status().isOk());
    }
}
