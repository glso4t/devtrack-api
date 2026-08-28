package com.devtrack.api.controller;

import com.devtrack.api.model.Task;
import com.devtrack.api.service.TaskService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
public class TaskController {

    private final TaskService taskService;

    //constructor
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/v1/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
}

    @GetMapping("/api/v1/tasks/{id}")
    public Task getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id);
}

    @PostMapping("/api/v1/tasks")
    @ResponseStatus(HttpStatus.CREATED) //201 Created
    public Task createTask(@RequestBody Task task) {
    return taskService.createTask(task);
}

    @PutMapping("/api/v1/tasks/{id}")
    public Task updateTask(
        @PathVariable Long id,
        @RequestBody Task task) {
    return taskService.updateTask(id, task);
}

    @PatchMapping("/api/v1/tasks/{id}")
    public Task patchTask(
        @PathVariable Long id,
        @RequestBody Task updatedTask) {
    return taskService.patchTask(id, updatedTask);
}

    @DeleteMapping("/api/v1/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build(); //Μας επιτρέπει να ελέγχουμε το HTTP response->δίνει 204 No Content
}

}
