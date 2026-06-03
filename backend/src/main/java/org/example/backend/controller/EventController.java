package org.example.backend.controller;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping()
    public EventResponseDTO create(@RequestBody EventRequestDTO eventRequestDTO) {
        return this.eventService.create(eventRequestDTO);
    }

    @GetMapping()
    public List<EventResponseDTO> getAll() {
        return this.eventService.getAll();
    }

    @GetMapping("/{id}")
    public EventResponseDTO getById(@PathVariable String id) {
        return this.eventService.getById(id);
    }
}
