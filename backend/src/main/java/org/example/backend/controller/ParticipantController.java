package org.example.backend.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.example.backend.dto.participant.ParticipantRequestDTO;
import org.example.backend.dto.participant.ParticipantResponseDTO;
import org.example.backend.dto.participant.PayDebtRequestDTO;
import org.example.backend.service.ParticipantService;
import org.example.backend.validation.ValidUUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/participant")
@Validated
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

    @GetMapping("{id}")
    public ParticipantResponseDTO getById(@PathVariable @ValidUUID String id) {
        return this.participantService.getById(id);
    }

    @PostMapping("{id}")
    public ResponseEntity<Map<String, String>> payDebt(@RequestBody @Valid PayDebtRequestDTO payDebtRequestDTO, @PathVariable @ValidUUID String id) {
        this.participantService.payDebt(id, payDebtRequestDTO.debt());
        return ResponseEntity.ok(Map.of("message", "debt was paid successfully"));
    }
}
