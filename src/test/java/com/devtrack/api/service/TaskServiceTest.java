package com.devtrack.api.service;
import com.devtrack.api.dto.TaskResponse;
import com.devtrack.api.model.Task;
import com.devtrack.api.exception.TaskNotFoundException;

import com.devtrack.api.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

}