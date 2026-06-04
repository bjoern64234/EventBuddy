package org.example.backend.service;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        id = "id";
        date = LocalDateTime.of(2027, Month.APRIL, 21, 23, 59, 59);

        eventRequestDTO = EventRequestDTO.builder()
                .name("test").isIndoor(true).date(date)
                .totalCost(33.5).imageUrl("imageUrl").build();

        event = Event.builder()
                .id(id).name("test").isIndoor(true).date(date)
                .totalCost(33.5).imageUrl("imageUrl").build();

        eventResponseDTO = EventResponseDTO.builder()
                .id(id).name("test").isIndoor(true)
                .totalCost(33.5).imageUrl("imageUrl").build();
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
        assertThrows(NoSuchElementException.class, () -> eventService.getById(id));
        verify(eventRepo).findById(id);
    }
}