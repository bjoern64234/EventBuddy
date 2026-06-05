package org.example.backend.controller;

import jakarta.validation.Valid;
import org.example.backend.dto.participant.ParticipantRequestDTO;
import org.example.backend.dto.participant.ParticipantResponseDTO;
import org.example.backend.service.ParticipantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping()
    public ParticipantResponseDTO create(@RequestBody @Valid ParticipantRequestDTO participantRequestDTO) {
        return this.participantService.create(participantRequestDTO);
    }

    @GetMapping()
    public List<ParticipantResponseDTO> getAll() {
        return this.participantService.getAll();
    }
}
