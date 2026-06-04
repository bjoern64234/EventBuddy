package org.example.backend.repository;

import org.example.backend.model.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepo extends MongoRepository<Participant, String> {
}
