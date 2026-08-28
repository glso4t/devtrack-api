package com.devtrack.api.repository;

import com.devtrack.api.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Repository
public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();

    public TaskRepository(){
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

    public List<Task> findAll() {
        return tasks;
    }

    public Task findById(Long id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public Task save(Task task) {
        tasks.add(task);
        return task;
    }

    public void deleteById(Long id) {
    /*for (Task task : tasks) {
        if (task.getId().equals(id)) {
            tasks.remove(task);
            return;
        }
    }
    throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Task not found"
    );
    Επικίνδυνο (ρίχνει ConcurrentModificationException αν συνεχιστεί το loop)   */
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId().equals(id)) {
                iterator.remove();
                return;
            }
        }
    }
}