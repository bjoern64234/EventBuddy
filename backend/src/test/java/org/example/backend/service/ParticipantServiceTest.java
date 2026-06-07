package org.example.backend.service;

import org.example.backend.dto.event.EventResponseDTO;
import org.example.backend.dto.participant.ParticipantRequestDTO;
import org.example.backend.dto.participant.ParticipantResponseDTO;
import org.example.backend.exceptions.event.EventNotFoundException;
import org.example.backend.exceptions.event.ParticipantsNotFoundException;
import org.example.backend.exceptions.participant.PayDebtConflictException;
import org.example.backend.model.Participant;
import org.example.backend.repository.ParticipantRepo;
import org.example.backend.utils.ParticipantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepo participantRepo;

    @Mock
    private ParticipantMapper participantMapper;

    @Mock
    private IdService idService;

    @InjectMocks
    private ParticipantService participantService;

    private String id;
    private Participant participant;
    private ParticipantRequestDTO requestDTO;
    private ParticipantResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        id = "550e8400-e29b-41d4-a716-446655440000";

        requestDTO = ParticipantRequestDTO.builder()
                .name("test").email("test@email.de").profileImageUrl("https://test.de").build();

        participant = Participant.builder()
                .id(id).name("test").email("test@email.de").profileImageUrl("https://test.de").dept(100.0).build();

        responseDTO = ParticipantResponseDTO.builder()
                .name("test").email("test@email.de").profileImageUrl("https://test.de").build();
    }

    @Test
    void create_shouldReturnParticipantResponseDTO() {
        // Given
        when(this.idService.generateId()).thenReturn(this.id);
        when(this.participantMapper.toParticipant(this.requestDTO, this.id)).thenReturn(this.participant);
        when(this.participantRepo.save(this.participant)).thenReturn(this.participant);
        when(this.participantMapper.toDTO(this.participant)).thenReturn(this.responseDTO);

        // When
        ParticipantResponseDTO actual = this.participantService.create(this.requestDTO);

        // Then
        assertEquals(this.responseDTO, actual);
        verify(this.idService).generateId();
        verify(this.participantMapper).toParticipant(this.requestDTO, this.id);
        verify(this.participantRepo).save(this.participant);
        verify(this.participantMapper).toDTO(this.participant);
    }

    @Test
    void getAll_shouldReturnListOfParticipantResponseDTOs() {
        // Given
        List<Participant> participants = List.of(this.participant);
        List<ParticipantResponseDTO> expected = List.of(this.responseDTO);

        when(this.participantRepo.findAll()).thenReturn(participants);
        when(this.participantMapper.toDTO(this.participant)).thenReturn(this.responseDTO);

        // When
        List<ParticipantResponseDTO> actual = this.participantService.getAll();

        // Then
        assertEquals(expected, actual);
        verify(this.participantRepo).findAll();
        verify(this.participantMapper).toDTO(this.participant);
    }

    @Test
    void getAll_shouldReturnEmptyList() {
        // Given
        when(this.participantRepo.findAll()).thenReturn(List.of());

        // When
        List<ParticipantResponseDTO> actual = this.participantService.getAll();

        // Then
        assertTrue(actual.isEmpty());
        verify(this.participantRepo).findAll();
    }

    @Test
    void getById_shouldReturnParticipantResponseDTO() {
        // Given
        when(this.participantRepo.findById(this.id)).thenReturn(Optional.of(this.participant));
        when(this.participantMapper.toDTO(this.participant)).thenReturn(this.responseDTO);

        // When
        ParticipantResponseDTO actual = this.participantService.getById(id);

        // Then
        assertEquals(responseDTO, actual);
        verify(participantRepo).findById(id);
        verify(participantMapper).toDTO(participant);
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        // Given
        when(this.participantRepo.findById(this.id)).thenReturn(Optional.empty());

        // Then
        assertThrows(ParticipantsNotFoundException.class, () -> this.participantService.getById(id));
        verify(this.participantRepo).findById(this.id);
    }

    @Test
    void payDebt_shouldReduceDept_whenAmountIsLessThanGivenDept() {
        // Given
        when(this.participantRepo.findById(this.id)).thenReturn(Optional.of(participant));

        // When
        this.participantService.payDebt(this.id, 50.0);

        // Then
        verify(this.participantRepo).findById(this.id);
        verify(this.participantRepo).save(participant.withDept(50.0));
    }

    @Test
    void payDebt_shouldThrowException_whenAmountIsGreaterThanGivenDept() {
        // Given
        when(this.participantRepo.findById(id)).thenReturn(Optional.of(this.participant));

        // Then
        assertThrows(PayDebtConflictException.class,
                () -> this.participantService.payDebt(id, 150.0));
        verify(this.participantRepo).findById(id);
    }
}