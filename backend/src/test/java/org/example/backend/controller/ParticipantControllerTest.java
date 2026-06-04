package org.example.backend.controller;

import org.example.backend.dto.participant.ParticipantRequestDTO;
import org.example.backend.dto.participant.ParticipantResponseDTO;
import org.example.backend.service.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParticipantController.class)
class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ParticipantService participantService;

    private String id;
    private ParticipantRequestDTO requestDTO;
    private ParticipantResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        id = "550e8400-e29b-41d4-a716-446655440000";

        requestDTO = ParticipantRequestDTO.builder()
                .name("test").email("test@email.de").profileImageUrl("https://test.de").build();

        responseDTO = ParticipantResponseDTO.builder()
                .name("test").email("test@email.de").profileImageUrl("https://test.de").build();
    }

    @Test
    void create_shouldReturnParticipantResponseDTO() throws Exception {
        when(participantService.create(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/participant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "test",
                                "email": "test@email.de",
                                "profileImageUrl": "https://test.de"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("test@email.de"))
                .andExpect(jsonPath("$.profileImageUrl").value("https://test.de"));

        verify(participantService).create(requestDTO);
    }

    @Test
    void getAll_shouldReturnListOfParticipantResponseDTOs() throws Exception {
        when(participantService.getAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/participant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].email").value("test@email.de"))
                .andExpect(jsonPath("$[0].profileImageUrl").value("https://test.de"));

        verify(participantService).getAll();
    }

    @Test
    void getAll_shouldReturnEmptyList() throws Exception {
        when(participantService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/participant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(participantService).getAll();
    }
}