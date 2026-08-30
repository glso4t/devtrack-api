package com.devtrack.api.exception;

import com.devtrack.api.dto.ErrorResponse;
import com.devtrack.api.dto.ValidationErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFound(
            TaskNotFoundException exception) {
        return new ErrorResponse(
                404,
                "TASK_NOT_FOUND",
                exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationErrors(MethodArgumentNotValidException exception) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setStatus(400);
        response.setError("VALIDATION_ERROR");
        response.setMessage("Request contains invalid fields");
        for (var error : exception.getBindingResult()
                .getFieldErrors()) {
                    response.getFields().put(
                        error.getField(),
                        error.getDefaultMessage()
                );
        }
        return response;
    }
}