package org.example.backend.service;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.exceptions.EventNotFoundException;
import org.example.backend.model.Event;
import org.example.backend.repository.EventRepo;
import org.example.backend.utils.EventMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepo eventRepo;
    @Mock
    private EventMapper eventMapper;
    @Mock
    private IdService idService;

    @InjectMocks
    private EventService eventService;

    private String id;
    private LocalDateTime date;
    private Event event;
    private EventRequestDTO eventRequestDTO;
    private EventResponseDTO eventResponseDTO;

    @BeforeEach
    void setUp() {
        id = "550e8400-e29b-41d4-a716-446655440000";
        date = LocalDateTime.of(2027, Month.APRIL, 21, 23, 59, 59);

        eventRequestDTO = EventRequestDTO.builder()
                .name("test").isIndoor(true).date(date)
                .totalCost(33.5).imageUrl("https://test.de").build();

        event = Event.builder()
                .id(id).name("test").isIndoor(true).date(date)
                .totalCost(33.5).imageUrl("https://test.de").build();

        eventResponseDTO = EventResponseDTO.builder()
                .id(id).name("test").isIndoor(true)
                .totalCost(33.5).imageUrl("https://test.de").build();
    }

    @Test
    void create_shouldReturnEventResponseDTO() {
        // Given
        when(idService.generateId()).thenReturn(id);
        when(eventMapper.toEntity(eventRequestDTO, id)).thenReturn(event);
        when(eventRepo.save(event)).thenReturn(event);
        when(eventMapper.toDTO(event)).thenReturn(eventResponseDTO);

        // When
        EventResponseDTO actual = eventService.create(eventRequestDTO);

        // Then
        assertEquals(eventResponseDTO, actual);
        verify(idService).generateId();
        verify(eventMapper).toEntity(eventRequestDTO, id);
        verify(eventRepo).save(event);
        verify(eventMapper).toDTO(event);
    }

    @Test
    void getAll_shouldReturnListOfEventResponseDTOs() {
        // Given
        List<Event> events = List.of(event);
        List<EventResponseDTO> expected = List.of(eventResponseDTO);

        when(eventRepo.findAll()).thenReturn(events);
        when(eventMapper.toDTO(event)).thenReturn(eventResponseDTO);

        // When
        List<EventResponseDTO> actual = eventService.getAll();

        // Then
        assertEquals(expected, actual);
        verify(eventRepo).findAll();
        verify(eventMapper).toDTO(event);
    }

    @Test
    void getAll_shouldReturnEmptyList() {
        // Given
        when(eventRepo.findAll()).thenReturn(List.of());

        // When
        List<EventResponseDTO> actual = eventService.getAll();

        // Then
        assertTrue(actual.isEmpty());
        verify(eventRepo).findAll();
    }

    @Test
    void getById_shouldReturnEventResponseDTO() {
        // Given
        when(eventRepo.findById(id)).thenReturn(Optional.of(event));
        when(eventMapper.toDTO(event)).thenReturn(eventResponseDTO);

        // When
        EventResponseDTO actual = eventService.getById(id);

        // Then
        assertEquals(eventResponseDTO, actual);
        verify(eventRepo).findById(id);
        verify(eventMapper).toDTO(event);
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        // Given
        when(eventRepo.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(EventNotFoundException.class, () -> eventService.getById(id));
        verify(eventRepo).findById(id);
    }

    @Test
    void addParticipant_shouldReturnEventResponseDTO_whenParticipantsIdsIsNull() {
        // Given
        Event eventWithoutParticipants = Event.builder()
                .id(id).name("test").isIndoor(true).date(date)
                .totalCost(33.5).imageUrl("https://test.de")
                .participantsIds(null).build();

        Event updatedEvent = eventWithoutParticipants.withParticipantsIds(Set.of(this.id));

        when(eventRepo.findById(id)).thenReturn(Optional.of(eventWithoutParticipants));
        when(eventRepo.save(updatedEvent)).thenReturn(updatedEvent);
        when(eventMapper.toDTO(updatedEvent)).thenReturn(eventResponseDTO);

        // When
        EventResponseDTO actual = eventService.addParticipant(id, this.id);

        // Then
        assertEquals(eventResponseDTO, actual);
        verify(eventRepo).findById(id);
        verify(eventRepo).save(updatedEvent);
        verify(eventMapper).toDTO(updatedEvent);
    }

    @Test
    void addParticipant_shouldAddToExistingList() {
        // Given
        Event eventWithExisting = Event.builder()
                .id(id).name("test").isIndoor(true).date(date)
                .totalCost(33.5).imageUrl("https://test.de")
                .participantsIds(new HashSet<>(List.of("550e8400-e29b-41d4-a716-446655440000"))).build();

        Event updatedEvent = eventWithExisting.withParticipantsIds(Set.of("550e8400-e29b-41d4-a716-446655440000", "550e8400-e29b-41d4-a716-446655440111"));

        when(eventRepo.findById(id)).thenReturn(Optional.of(eventWithExisting));
        when(eventRepo.save(updatedEvent)).thenReturn(updatedEvent);
        when(eventMapper.toDTO(updatedEvent)).thenReturn(eventResponseDTO);

        // When
        EventResponseDTO actual = eventService.addParticipant(id, "550e8400-e29b-41d4-a716-446655440111");

        // Then
        assertEquals(eventResponseDTO, actual);
        verify(eventRepo).findById(id);
        verify(eventRepo).save(updatedEvent);
        verify(eventMapper).toDTO(updatedEvent);
    }
}