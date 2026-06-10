package org.example.backend.utils;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.model.Event;
import org.springframework.stereotype.Service;

@Service
public class EventMapper {

    public Event toEntity(EventRequestDTO eventRequestDTO, String id) {
        return Event.builder().build()
                .withId(id)
                .withName(eventRequestDTO.name())
                .withIndoor(eventRequestDTO.isIndoor())
                .withDate(eventRequestDTO.date())
                .withTotalCost(eventRequestDTO.totalCost())
                .withImageUrl(eventRequestDTO.imageUrl());
    }

    public EventResponseDTO toDTO(Event event) {
        return EventResponseDTO.builder().build()
                .withId(event.id())
                .withName(event.name())
                .withLocation(event.location())
                .withIndoor(event.isIndoor())
                .withTotalCost(event.totalCost())
                .withRemainingCost(event.remainingCost())
                .withImageUrl(event.imageUrl())
                .withParticipantsIds(event.participantsIds())
                .withTasks(event.tasks());
    }
}