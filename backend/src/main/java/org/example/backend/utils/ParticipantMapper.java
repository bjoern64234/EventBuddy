package org.example.backend.utils;

import org.example.backend.dto.participant.ParticipantRequestDTO;
import org.example.backend.model.Participant;
import org.springframework.stereotype.Service;

@Service
public class ParticipantMapper {

    public Participant toParticipant(ParticipantRequestDTO participantRequestDTO, String id) {
        return Participant.builder().build()
                .withId(id)
                .withName(participantRequestDTO.name())
                .withEmail(participantRequestDTO.email())
                .withProfileImageUrl(participantRequestDTO.profileImageUrl());
    }
}
