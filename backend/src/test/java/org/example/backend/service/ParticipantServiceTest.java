package org.example.backend.service;

import org.example.backend.dto.participant.ParticipantRequestDTO;
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

    private ParticipantRequestDTO requestDTO;
    private Participant participant;
    private String id;

    @BeforeEach
    void setUp() {
        id = "550e8400-e29b-41d4-a716-446655440000";
        requestDTO = ParticipantRequestDTO.builder().name("test").email("test@email.de").profileImageUrl("https://test.de").build();
        participant = Participant.builder().id(id).name("test").email("test@email.de").profileImageUrl("https://test.de").build();
    }

    @Test
    void create_shouldReturnSavedParticipant() {
        // Given
        when(this.idService.generateId()).thenReturn(this.id);
        when(this.participantMapper.toParticipant(this.requestDTO, this.id)).thenReturn(this.participant);
        when(this.participantRepo.save(this.participant)).thenReturn(this.participant);

        // When
        Participant actual = this.participantService.create(this.requestDTO);

        // Then
        assertNotNull(actual);
        assertEquals(this.id, actual.id());
        assertEquals("test", actual.name());
        verify(this.idService).generateId();
        verify(this.participantMapper).toParticipant(this.requestDTO, this.id);
        verify(this.participantRepo).save(this.participant);
    }

    @Test
    void create_shouldCallRepoSaveExactlyOnce() {
        // Given
        when(this.idService.generateId()).thenReturn(this.id);
        when(this.participantMapper.toParticipant(this.requestDTO, id)).thenReturn(this.participant);
        when(this.participantRepo.save(this.participant)).thenReturn(this.participant);

        // When
        this.participantService.create(this.requestDTO);

        // Then
        verify(this.participantRepo, times(1)).save(this.participant);
    }

    @Test
    void create_shouldUseGeneratedIdFromIdService() {
        // Given
        String expectedId = "550e8400-e29b-41d4-a716-446655440444";
        Participant participantWithNewId = new Participant(expectedId, "test", "test@email.de", "https://test.de");

        when(this.idService.generateId()).thenReturn(expectedId);
        when(this.participantMapper.toParticipant(this.requestDTO, expectedId)).thenReturn(participantWithNewId);
        when(this.participantRepo.save(participantWithNewId)).thenReturn(participantWithNewId);

        // When
        Participant actual = this.participantService.create(this.requestDTO);

        // Then
        assertEquals(expectedId, actual.id());
        verify(this.idService).generateId();
    }

    @Test
    void getAll_shouldReturnListOfParticipants() {
        // Given
        List<Participant> participants = List.of(
                new Participant("550e8400-e29b-41d4-a716-446655440000", "Alice", "alice@example.com", "https://test.de"),
                new Participant("550e8400-e29b-41d4-a716-446655440011", "Bob", "bob@example.com", "https://test.de")
        );
        when(this.participantRepo.findAll()).thenReturn(participants);

        // When
        List<Participant> actual = this.participantService.getAll();

        // Then
        assertNotNull(actual);
        assertEquals(2, actual.size());
        verify(this.participantRepo).findAll();
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoParticipantsExist() {
        // Given
        when(this.participantRepo.findAll()).thenReturn(List.of());

        // When
        List<Participant> actual = this.participantService.getAll();

        // Then
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
        verify(this.participantRepo).findAll();
    }

    @Test
    void getAll_shouldCallRepoFindAllExactlyOnce() {
        // Given
        when(this.participantRepo.findAll()).thenReturn(List.of());

        // When
        this.participantService.getAll();

        // Then
        verify(this.participantRepo, times(1)).findAll();
    }
}