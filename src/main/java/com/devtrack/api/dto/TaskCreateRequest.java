package com.devtrack.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class TaskCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(
        min = 3,
        max = 100,
        message = "Title must be between 3 and 100 characters"
    )
    private String title;

    @Size(
    max = 500,
    message = "Description cannot exceed 500 characters"
    )
    private String description;

    private Boolean completed;


    public TaskCreateRequest() {
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