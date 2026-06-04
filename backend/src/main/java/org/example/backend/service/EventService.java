package org.example.backend.service;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.repository.EventRepo;
import org.example.backend.utils.EventMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepo eventRepo;
    private final EventMapper eventMapper;
    private final IdService idService;

    public EventService(EventRepo eventRepo, EventMapper eventMapper, IdService idService) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;
        this.idService = idService;
    }

    public EventResponseDTO create(EventRequestDTO eventRequestDTO) {
        return null;
    }

    public List<EventResponseDTO> getAll() {
        return null;
    }

    public EventResponseDTO getById(String id) {
        return null;
    }
}
