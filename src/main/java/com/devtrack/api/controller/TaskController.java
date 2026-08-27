package com.devtrack.api.controller;

import com.devtrack.api.model.Task;
import com.devtrack.api.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TaskController {

    private final TaskService taskService;

    //constructor
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/v1/tasks")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

@PostMapping("/api/v1/tasks")
public Task createTask(@RequestBody Task task) {
    return taskService.createTask(task);
}

}
