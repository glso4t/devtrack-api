package com.devtrack.api.controller;

import com.devtrack.api.dto.TaskCreateRequest;
import com.devtrack.api.dto.TaskResponse;
import com.devtrack.api.dto.TaskUpdateRequest;
//import com.devtrack.api.model.Task;
import com.devtrack.api.service.TaskService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    //constructor
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //GET
    @GetMapping("/api/v1/tasks")
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
}

    //GET BY ID
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id);
}

    //POST
    @PostMapping("/api/v1/tasks")
    @ResponseStatus(HttpStatus.CREATED) //201 Created
    public TaskResponse createTask(@RequestBody TaskCreateRequest task) {
    return taskService.createTask(task);
}

    //PUT
    @PutMapping("/{id}")
    public TaskResponse updateTask(
        @PathVariable Long id,
        @RequestBody TaskUpdateRequest task) {
    return taskService.updateTask(id, task);
}

    //PATCH
    @PatchMapping("/{id}")
    public TaskResponse patchTask(
        @PathVariable Long id,
        @RequestBody TaskUpdateRequest updatedTask) {
    return taskService.patchTask(id, updatedTask);
}

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build(); //Μας επιτρέπει να ελέγχουμε το HTTP response->δίνει 204 No Content
}

}
