package com.devtrack.api.service;

import com.devtrack.api.dto.TaskCreateRequest;
import com.devtrack.api.dto.TaskResponse;
import com.devtrack.api.dto.TaskUpdateRequest;
import com.devtrack.api.model.Task;
import com.devtrack.api.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    //CONSTRUCTOR DEPENDENCY INJECTION
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    private TaskResponse toResponse(Task task) {        //για αποφυγή επαναλήψεων στα return
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCompleted(),
                task.getCreatedAt()
        );
    }


    //GET
    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> responses = new ArrayList<>();
        for (Task task : taskRepository.findAll()) {
            TaskResponse response = new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getCompleted(),
                    task.getCreatedAt()
            );
            responses.add(response);
        }
        return responses;
        /*--Before DTOs--
        return taskRepository.findAll();*/
    }


    //GET BY ID
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Task not found"
                        )
                );
        return toResponse(task);
    }
        /*--Before DTOs--
    return taskRepository.findById(id)  //findById returns Optional<Task> instead of Task
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Task not found"
                    )
            );*/
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


    //POST
    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.getCompleted());
        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
        /*--Before DTOs--
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);*/
    }


    //PUT
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Task not found"
                        )
                );
        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setCompleted(request.getCompleted());
        Task savedTask = taskRepository.save(existingTask);
        return toResponse(savedTask);
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
    public TaskResponse patchTask(Long id, TaskUpdateRequest request) {
        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Task not found"
                    )
            );
        if (request.getTitle() != null) {
            existingTask.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            existingTask.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            existingTask.setCompleted(request.getCompleted());
        }
        Task savedTask = taskRepository.save(existingTask);
        return toResponse(savedTask);        
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

