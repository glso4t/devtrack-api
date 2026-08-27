package com.devtrack.api.service;

import com.devtrack.api.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    public List<Task> getTasks() {

        List<Task> tasks = new ArrayList<>();

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
        task1.setCreatedAt(null);


        tasks.add(task1);
        tasks.add(task2);

        return tasks;
    }
}