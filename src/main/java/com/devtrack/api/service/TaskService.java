package com.devtrack.api.service;

import com.devtrack.api.model.Task;
import com.devtrack.api.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    return taskRepository.findById(id)  //findById returns Optional<Task> instead of Task
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Task not found"
                    )
            );
        /*Simpler way (χρειάζεται import java.util.Optional;)
    // 1. Παίρνουμε το Optional "κουτί" από το repository
        Optional<Task> optionalTask = taskRepository.findById(id);
    // 2. Ελέγχουμε αν το κουτί είναι άδειο
        if (optionalTask.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
    // 3. Αν δεν ήταν άδειο, βγάζουμε το Task από το κουτί και το επιστρέφουμε
        return optionalTask.get();
    } */

        /*--Manually before JPA--
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
        return task;*/
    }


    //POST
    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }


    //PUT
    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Task not found"
                        )
                );
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());
        return taskRepository.save(existingTask);
        
        /*--Manually before JPA--
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
        existingTask.setCreatedAt(updatedTask.getCreatedAt());
        return existingTask;*/
    }


    //PATCH
    public Task patchTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Task not found"
                    )
            );
        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }

        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }

        if (updatedTask.getCompleted() != null) {
            existingTask.setCompleted(updatedTask.getCompleted());
        }
        return taskRepository.save(existingTask);
        /* --Manually before JPA--
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
        if (updatedTask.getCreatedAt() != null) {
            existingTask.setCreatedAt(updatedTask.getCreatedAt());
        }
        return existingTask;*/
    }


    //DELETE
    public void deleteTask(Long id) {
        
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Task not found"
            );
        }
        taskRepository.deleteById(id);
        
        /*--Manually before JPA--
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task not found"
            );
        }
        taskRepository.deleteById(id); */
    }

}

