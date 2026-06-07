package org.example.backend.exceptions.event;

public class ParticipantsNotFoundException extends RuntimeException {
    public ParticipantsNotFoundException(String eventName) {
        super("The are not participants found for event " + eventName);
    }
}
