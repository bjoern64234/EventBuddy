package org.example.backend.controller;

import org.example.backend.dto.event.EventRequestDTO;
import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.exceptions.EventNotFoundException;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
                .totalCost(33.5).imageUrl("imageUrl").build();

        eventResponseDTO = EventResponseDTO.builder()
                .id(id).name("test").isIndoor(true)
                .totalCost(33.5).imageUrl("imageUrl").build();
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
                                "imageUrl": "imageUrl"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.isIndoor").value(true))
                .andExpect(jsonPath("$.totalCost").value(33.5))
                .andExpect(jsonPath("$.imageUrl").value("imageUrl"));

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
                .andExpect(jsonPath("$[0].imageUrl").value("imageUrl"));

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
                .andExpect(jsonPath("$.imageUrl").value("imageUrl"));

        verify(eventService).getById(id);
    }

    @Test
    void getById_shouldReturn404_whenNotFound() throws Exception {
        when(eventService.getById(id)).thenThrow(new EventNotFoundException(id));

        mockMvc.perform(get("/api/event/{id}", id))
                .andExpect(status().isNotFound());

        verify(eventService).getById(id);
    }
}