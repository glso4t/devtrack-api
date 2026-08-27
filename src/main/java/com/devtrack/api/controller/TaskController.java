package com.devtrack.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TaskController {
    
    @GetMapping("/api/v1/tasks")
    public String getTasks() {
        return "Tasks";
    }
    
}
