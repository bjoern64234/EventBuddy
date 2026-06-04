package org.example.backend.controller;

import org.example.backend.dto.participant.ParticipantRequestDTO;
import org.example.backend.dto.participant.ParticipantResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

    @PostMapping()
    public ParticipantResponseDTO create(@RequestBody ParticipantRequestDTO participantRequestDTO) {
        return null;
    }

    @GetMapping()
    public List<ParticipantResponseDTO> getAll() {
        return null;
    }
}
