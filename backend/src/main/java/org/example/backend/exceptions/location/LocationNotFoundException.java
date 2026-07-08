package org.example.backend.exceptions.location;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(String location) {
        super("Could not find location with name " + location);
    }
}
