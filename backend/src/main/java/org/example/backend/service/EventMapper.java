package org.example.backend.service;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.model.Event;
import org.springframework.stereotype.Service;

@Service
public class EventMapper {

    public Event toEntity(EventRequestDTO eventRequestDTO, String id) {
        return null;
    }

    public EventResponseDTO toDTO(Event event) {
        return null;
    }
}
