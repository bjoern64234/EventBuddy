package org.example.backend.exceptions.participant;

public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException(String id) {
        super("The participant " + id + " was not found");
    }
}
