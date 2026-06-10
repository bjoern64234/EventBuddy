package org.example.backend.service;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.dto.task.TaskRequestDTO;
import org.example.backend.exceptions.event.EventNotFoundException;
import org.example.backend.exceptions.event.ParticipantsNotFoundException;
import org.example.backend.model.Event;
import org.example.backend.model.Participant;
import org.example.backend.model.Task;
import org.example.backend.repository.EventRepo;
import org.example.backend.repository.ParticipantRepo;
import org.example.backend.utils.EventMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {

    private final EventRepo eventRepo;
    private final ParticipantRepo participantRepo;
    private final EventMapper eventMapper;
    private final IdService idService;

    public EventService(EventRepo eventRepo, ParticipantRepo participantRepo, EventMapper eventMapper, IdService idService) {
        this.eventRepo = eventRepo;
        this.participantRepo = participantRepo;
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

    public EventResponseDTO addParticipant(String eventId, String participantId) {
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        Set<String> participantIds = (event.participantsIds() == null) ? new HashSet<>() : event.participantsIds();
        participantIds.add(participantId);

        return this.eventMapper.toDTO(this.eventRepo.save(event.withParticipantsIds(participantIds)));
    }

    public void splitCosts(String eventId) {
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        Set<String> participantsIds = event.participantsIds();

        if(participantsIds.isEmpty()) {
            throw new ParticipantsNotFoundException(event.name());
        }

        List<Participant> existingParticipants = participantsIds
                .stream()
                .map(participantsId -> this.participantRepo.findById(participantsId).orElseThrow(() -> new ParticipantsNotFoundException(participantsId)))
                .toList();

        double costsForEachParticipant = event.totalCost() / existingParticipants.size();

        existingParticipants.forEach(participant -> {
            this.participantRepo.save(participant.withDept(costsForEachParticipant));
        });
    }

    public EventResponseDTO addTask(String eventId, TaskRequestDTO taskDTO) {
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        List<Task> tasks = (event.tasks() == null) ? new ArrayList<>() : event.tasks();

        tasks.add(Task.builder().id(this.idService.generateId()).title(taskDTO.title()).completed(false).build());

        return this.eventMapper.toDTO(this.eventRepo.save(event.withTasks(tasks)));
    }
}
