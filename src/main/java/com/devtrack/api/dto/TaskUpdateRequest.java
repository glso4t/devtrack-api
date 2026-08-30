package com.devtrack.api.dto;

//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class TaskUpdateRequest {

    @Size(min = 3, max = 100)
    private String title;
    
    @Size(
        max = 500,
        message = "Description cannot exceed 500 characters"
    )
    private String description;
    
    private Boolean completed;

    public TaskUpdateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}