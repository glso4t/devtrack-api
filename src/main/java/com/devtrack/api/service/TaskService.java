package com.devtrack.api.service;

import com.devtrack.api.model.Task;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public TaskService(){
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Learn Spring");
        task1.setDescription("Study Spring Boot");
        task1.setCompleted(false);
        task1.setCreatedAt(null);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Learn SQL");
        task2.setDescription("Practice SQL queries");
        task2.setCompleted(true);
        task2.setCreatedAt(null);

        tasks.add(task1);
        tasks.add(task2);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskById(Long id) {
    for (Task task : tasks) {
        if (task.getId().equals(id)) {
            return task;
        }
    }
    //return null;
    throw new ResponseStatusException(
    HttpStatus.NOT_FOUND,
    "Task not found"
    );
}

    public Task createTask(Task task) {
        tasks.add(task);
        return task;
}



}