package org.example.backend.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.service.EventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@Validated
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping()
    public EventResponseDTO create(@RequestBody @Valid EventRequestDTO eventRequestDTO) {
        return this.eventService.create(eventRequestDTO);
    }

    @GetMapping()
    public List<EventResponseDTO> getAll() {
        return this.eventService.getAll();
    }

    @GetMapping("/{id}")
    public EventResponseDTO getById(@PathVariable @Pattern(regexp = "[0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}", message = "The id must by a valid uuid") String id) {
        return this.eventService.getById(id);
    }
}
