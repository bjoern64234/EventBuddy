package org.example.backend.exceptions;

import java.util.NoSuchElementException;

public class EventNotFoundException extends NoSuchElementException {
    public EventNotFoundException(String id) {
        super("A event with id " + id + " not found");
    }
}
