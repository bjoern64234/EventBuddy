package org.example.backend.service;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.exceptions.EventNotFoundException;
import org.example.backend.model.Event;
import org.example.backend.repository.EventRepo;
import org.example.backend.utils.EventMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        Event newEvent = this.eventRepo.save(this.eventMapper.toEntity(eventRequestDTO, this.idService.generateId()));
        return this.eventMapper.toDTO(newEvent);
    }

    public List<EventResponseDTO> getAll() {
        List<Event> events = eventRepo.findAll();
        List<EventResponseDTO> eventResponseDTOS = new ArrayList<>();

        events.forEach(event -> eventResponseDTOS.add(this.eventMapper.toDTO(event)));

        return eventResponseDTOS;
    }

    public EventResponseDTO getById(String id) {
        Event event = eventRepo.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        return this.eventMapper.toDTO(event);
    }
}
