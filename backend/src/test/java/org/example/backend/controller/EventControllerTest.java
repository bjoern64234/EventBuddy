package org.example.backend.controller;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.exceptions.event.EventNotFoundException;
import org.example.backend.exceptions.event.ParticipantsNotFoundException;
import org.example.backend.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    private String id;
    private LocalDateTime date;
    private EventRequestDTO eventRequestDTO;
    private EventResponseDTO eventResponseDTO;

    @BeforeEach
    void setUp() {
        id = "550e8400-e29b-41d4-a716-446655440000";
        date = LocalDateTime.of(2027, Month.APRIL, 21, 23, 59, 59);

        eventRequestDTO = EventRequestDTO.builder()
                .name("test").isIndoor(true).date(date)
                .totalCost(33.5).imageUrl("https://test.de").build();

        eventResponseDTO = EventResponseDTO.builder()
                .id(id).name("test").isIndoor(true)
                .totalCost(33.5).imageUrl("https://test.de").build();
    }

    @Test
    void create_shouldReturnEventResponseDTO() throws Exception {
        when(eventService.create(eventRequestDTO)).thenReturn(eventResponseDTO);

        mockMvc.perform(post("/api/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "test",
                                "isIndoor": true,
                                "date": "2027-04-21T23:59:59",
                                "totalCost": 33.5,
                                "imageUrl": "https://test.de"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.isIndoor").value(true))
                .andExpect(jsonPath("$.totalCost").value(33.5))
                .andExpect(jsonPath("$.imageUrl").value("https://test.de"));

        verify(eventService).create(eventRequestDTO);
    }

    @Test
    void getAll_shouldReturnListOfEventResponseDTOs() throws Exception {
        when(eventService.getAll()).thenReturn(List.of(eventResponseDTO));

        mockMvc.perform(get("/api/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].isIndoor").value(true))
                .andExpect(jsonPath("$[0].totalCost").value(33.5))
                .andExpect(jsonPath("$[0].imageUrl").value("https://test.de"));

        verify(eventService).getAll();
    }

    @Test
    void getAll_shouldReturnEmptyList() throws Exception {
        when(eventService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(eventService).getAll();
    }

    @Test
    void getById_shouldReturnEventResponseDTO() throws Exception {
        when(eventService.getById(id)).thenReturn(eventResponseDTO);

        mockMvc.perform(get("/api/event/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.isIndoor").value(true))
                .andExpect(jsonPath("$.totalCost").value(33.5))
                .andExpect(jsonPath("$.imageUrl").value("https://test.de"));

        verify(eventService).getById(id);
    }

    @Test
    void getById_shouldReturn404_whenNotFound() throws Exception {
        when(eventService.getById(id)).thenThrow(new EventNotFoundException(id));

        mockMvc.perform(get("/api/event/{id}", id))
                .andExpect(status().isNotFound());

        verify(eventService).getById(id);
    }

    @Test
    void addParticipantToEvent_shouldReturnEventResponseDTO() throws Exception {
        // Given
        String participantId = "660e8400-e29b-41d4-a716-446655440000";
        when(eventService.addParticipant(id, participantId)).thenReturn(eventResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/event/{eventId}/participant/{participantId}", id, participantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.isIndoor").value(true))
                .andExpect(jsonPath("$.totalCost").value(33.5))
                .andExpect(jsonPath("$.imageUrl").value("https://test.de"));

        verify(eventService).addParticipant(id, participantId);
    }

    @Test
    void addParticipantToEvent_shouldReturn404_whenEventNotFound() throws Exception {
        // Given
        String participantId = "660e8400-e29b-41d4-a716-446655440000";
        when(eventService.addParticipant(id, participantId)).thenThrow(new EventNotFoundException(id));

        // When & Then
        mockMvc.perform(get("/api/event/{eventId}/participant/{participantId}", id, participantId))
                .andExpect(status().isNotFound());

        verify(eventService).addParticipant(id, participantId);
    }

    @Test
    void splitCosts_shouldReturn200WithMessage() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/event/{eventId}/split-costs", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("split costs successfully"));

        verify(eventService).splitCosts(id);
    }

    @Test
    void splitCosts_shouldReturn404_whenEventNotFound() throws Exception {
        // Given
        doThrow(new EventNotFoundException(id)).when(eventService).splitCosts(id);

        // When & Then
        mockMvc.perform(get("/api/event/{eventId}/split-costs", id))
                .andExpect(status().isNotFound());

        verify(eventService).splitCosts(id);
    }

    @Test
    void splitCosts_shouldReturn400_whenIdIsInvalid() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/event/{eventId}/split-costs", "invalid-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void splitCosts_shouldReturn404_whenParticipantsNotFound() throws Exception {
        // Given
        doThrow(new ParticipantsNotFoundException(id)).when(eventService).splitCosts(id);

        // When & Then
        mockMvc.perform(get("/api/event/{eventId}/split-costs", id))
                .andExpect(status().isNotFound());

        verify(eventService).splitCosts(id);
    }

    @Test
    void addTask_shouldReturnEventResponseDTO() throws Exception {
        // Given
        when(eventService.addTask(eq(id), any())).thenReturn(eventResponseDTO);

        // When & Then
        mockMvc.perform(post("/api/event/{eventId}/task", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "title": "Kuchen backen"
                        }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.isIndoor").value(true))
                .andExpect(jsonPath("$.totalCost").value(33.5))
                .andExpect(jsonPath("$.imageUrl").value("https://test.de"));

        verify(eventService).addTask(eq(id), any());
    }

    @Test
    void addTask_shouldReturn404_whenEventNotFound() throws Exception {
        // Given
        when(eventService.addTask(eq(id), any())).thenThrow(new EventNotFoundException(id));

        // When & Then
        mockMvc.perform(post("/api/event/{eventId}/task", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "title": "Kuchen backen"
                        }
                    """))
                .andExpect(status().isNotFound());

        verify(eventService).addTask(eq(id), any());
    }

    @Test
    void addTask_shouldReturn400_whenTitleIsMissing() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/event/{eventId}/task", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {}
                    """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void completeTask_shouldReturnEventResponseDTO() throws Exception {
        // Given
        String taskId = "660e8400-e29b-41d4-a716-446655440000";
        when(eventService.completeTask(id, taskId)).thenReturn(eventResponseDTO);

        // When & Then
        mockMvc.perform(put("/api/event/{eventId}/task/{taskId}", id, taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.isIndoor").value(true))
                .andExpect(jsonPath("$.totalCost").value(33.5))
                .andExpect(jsonPath("$.imageUrl").value("https://test.de"));

        verify(eventService).completeTask(id, taskId);
    }

    @Test
    void completeTask_shouldReturn404_whenEventNotFound() throws Exception {
        // Given
        String taskId = "660e8400-e29b-41d4-a716-446655440000";
        when(eventService.completeTask(id, taskId)).thenThrow(new EventNotFoundException(id));

        // When & Then
        mockMvc.perform(put("/api/event/{eventId}/task/{taskId}", id, taskId))
                .andExpect(status().isNotFound());

        verify(eventService).completeTask(id, taskId);
    }

    @Test
    void completeTask_shouldReturn400_whenEventIdIsInvalid() throws Exception {
        // Given
        String taskId = "660e8400-e29b-41d4-a716-446655440000";

        // When & Then
        mockMvc.perform(put("/api/event/{eventId}/task/{taskId}", "invalid-id", taskId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void completeTask_shouldReturn400_whenTaskIdIsInvalid() throws Exception {
        // When & Then
        mockMvc.perform(put("/api/event/{eventId}/task/{taskId}", id, "invalid-id"))
                .andExpect(status().isBadRequest());
    }
}