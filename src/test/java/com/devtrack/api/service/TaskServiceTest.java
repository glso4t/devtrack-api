package com.devtrack.api.service;

import com.devtrack.api.dto.TaskCreateRequest;
import com.devtrack.api.dto.TaskResponse;
import com.devtrack.api.dto.TaskUpdateRequest;
import com.devtrack.api.model.Task;
import com.devtrack.api.exception.TaskNotFoundException;
import com.devtrack.api.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldReturnTaskWhenTaskExists() {
        // Arrange
        Task task = new Task();

        task.setId(1L);
        task.setTitle("Learn Testing");
        task.setDescription("Practice JUnit");
        task.setCompleted(false);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        // Act
        TaskResponse response =
                taskService.getTaskById(1L);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Learn Testing", response.getTitle());
        assertEquals("Practice JUnit", response.getDescription());
        assertEquals(false, response.getCompleted());
    }

    @Test
    void shouldThrowExceptionWhenTaskDoesNotExist() {
        // Arrange
        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(999L) //act
        );
        verify(taskRepository).findById(999L);  //Επιβεβαίωσε ότι το Service πράγματι κάλεσε το repository με ID 999

    }
    
    //Test getAllTasks()
    @Test
    void shouldReturnAllTasks() {
        // Arrange
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Learn Java");
        task1.setDescription("Practice OOP");
        task1.setCompleted(true);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Learn SQL");
        task2.setDescription("Practice queries");
        task2.setCompleted(false);

        when(taskRepository.findAll())
                .thenReturn(List.of(task1, task2));

        // Act
        List<TaskResponse> responses =
                taskService.getAllTasks();

        // Assert
        assertEquals(2, responses.size());
        assertEquals("Learn Java", responses.get(0).getTitle());
        assertEquals("Learn SQL", responses.get(1).getTitle());

        verify(taskRepository).findAll();
    }

    //Test createTask()
    @Test
    void shouldCreateTask() {
        // Arrange
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Learn Testing");
        request.setDescription("Practice JUnit");
        request.setCompleted(false);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Learn Testing");
        savedTask.setDescription("Practice JUnit");
        savedTask.setCompleted(false);

        when(taskRepository.save(any(Task.class))) //Δεν με ενδιαφέρει ποιο ακριβώς Task θα δοθεί στο save(), αρκεί να είναι Task
                .thenReturn(savedTask);

        // Act
        TaskResponse response =
                taskService.createTask(request);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Learn Testing", response.getTitle());
        assertEquals("Practice JUnit", response.getDescription());
        assertEquals(false, response.getCompleted());

        verify(taskRepository).save(any(Task.class));
    }

    //Test updateTask()
    @Test
    void shouldUpdateTask() {
        // Arrange
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old title");
        existingTask.setDescription("Old description");
        existingTask.setCompleted(false);

        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("New title");
        request.setDescription("New description");
        request.setCompleted(true);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask))
                .thenReturn(existingTask);

        // Act
        TaskResponse response =
                taskService.updateTask(1L, request);

        // Assert
        assertEquals("New title", response.getTitle());
        assertEquals("New description", response.getDescription());
        assertEquals(true, response.getCompleted());

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(existingTask);
    }

    //Test updateTask() όταν δεν υπάρχει
    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingTask() {
        // Arrange
        TaskUpdateRequest request = new TaskUpdateRequest();

        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.updateTask(999L, request)
        );

        verify(taskRepository).findById(999L);
        verify(taskRepository, never()).save(any(Task.class));  //Αν το Task δεν υπάρχει, μην κάνεις save
    }

    //Test PATCH
    @Test
    void shouldPartiallyUpdateTask() {
        // Arrange
        Task existingTask = new Task();

        existingTask.setId(1L);
        existingTask.setTitle("Learn Spring");
        existingTask.setDescription("REST");
        existingTask.setCompleted(false);

        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setCompleted(true);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask))
                .thenReturn(existingTask);

        // Act
        TaskResponse response =
                taskService.patchTask(1L, request);

        // Assert
        assertEquals("Learn Spring", response.getTitle());
        assertEquals("REST", response.getDescription());
        assertTrue(response.getCompleted());

        verify(taskRepository).save(existingTask);
    }

    //Test DELETE
    @Test
    void shouldDeleteTask() {
        // Arrange
        when(taskRepository.existsById(1L))
                .thenReturn(true);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository).existsById(1L);
        verify(taskRepository).deleteById(1L);
    }

    //Test DELETE non existent Task
        @Test
    void shouldThrowExceptionWhenDeletingNonExistingTask() {
        // Arrange
        when(taskRepository.existsById(999L))
                .thenReturn(false);

        // Act & Assert
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(999L)
        );

        verify(taskRepository).existsById(999L);
        verify(taskRepository, never()).deleteById(999L);
    }
}