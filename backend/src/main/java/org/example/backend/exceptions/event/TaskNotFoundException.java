package org.example.backend.exceptions.event;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String id) {
        super("Task with id " + id + " not found");
    }
}
