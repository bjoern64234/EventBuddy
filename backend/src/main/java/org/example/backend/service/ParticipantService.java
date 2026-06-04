package org.example.backend.service;

import org.example.backend.dto.participant.ParticipantRequestDTO;
import org.example.backend.model.Participant;
import org.example.backend.repository.ParticipantRepo;
import org.example.backend.utils.ParticipantMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {

    private final ParticipantRepo participantRepo;
    private final ParticipantMapper participantMapper;
    private final IdService idService;

    public ParticipantService(ParticipantRepo participantRepo, ParticipantMapper participantMapper, IdService idService) {
        this.participantRepo = participantRepo;
        this.participantMapper = participantMapper;
        this.idService = idService;
    }

    public Participant create(ParticipantRequestDTO participantRequestDTO) {
        return null;
    }

    public List<Participant> getAll() {
        return null;
    }
}
