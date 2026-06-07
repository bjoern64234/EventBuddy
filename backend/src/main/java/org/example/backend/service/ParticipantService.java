package org.example.backend.service;

import org.example.backend.dto.participant.ParticipantRequestDTO;
import org.example.backend.dto.participant.ParticipantResponseDTO;
import org.example.backend.exceptions.participant.ParticipantNotFoundException;
import org.example.backend.exceptions.participant.PayDebtConflictException;
import org.example.backend.model.Participant;
import org.example.backend.repository.ParticipantRepo;
import org.example.backend.utils.ParticipantMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ParticipantResponseDTO create(ParticipantRequestDTO participantRequestDTO) {
        return this.participantMapper.toDTO(this.participantRepo.save(this.participantMapper.toParticipant(participantRequestDTO, this.idService.generateId())));
    }

    public List<ParticipantResponseDTO> getAll() {
        List<Participant> participants = this.participantRepo.findAll();
        List<ParticipantResponseDTO> participantResponseDTOS = new ArrayList<>();

        participants.forEach(participant -> participantResponseDTOS.add(this.participantMapper.toDTO(participant)));

        return participantResponseDTOS;
    }

    public ParticipantResponseDTO getById(String id) {
        Participant participant = this.participantRepo.findById(id).orElseThrow(() -> new ParticipantNotFoundException(id));
        return this.participantMapper.toDTO(participant);
    }

    public void payDebt(String participantId, double amount) {
        Participant participant = this.participantRepo.findById(participantId).orElseThrow(() -> new ParticipantNotFoundException(participantId));

        if (amount > participant.dept()) {
            throw new PayDebtConflictException(amount);
        }

        double currentDebt = participant.dept() - amount;

        this.participantRepo.save(participant.withDept(currentDebt));
    }
}
