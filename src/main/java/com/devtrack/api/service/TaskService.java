package com.devtrack.api.service;

import com.devtrack.api.model.Task;
import com.devtrack.api.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    //CONSTRUCTOR DEPENDENCY INJECTION
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //GET
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //GET BY ID
    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
        return task;
    }

    //POST
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    //PUT
    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id);
        if (existingTask == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());
        return existingTask;
    }

    //PATCH
    public Task patchTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id);
        if (existingTask == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
            if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getCompleted() != null) {
            existingTask.setCompleted(updatedTask.getCompleted());
        }
        return existingTask;
    }

    //DELETE
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
        taskRepository.deleteById(id);
    }

}

