package com.dizertatie.reactiveinvestigations.repository;

import com.dizertatie.reactiveinvestigations.persistance.model.ParticipantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepo extends JpaRepository<ParticipantModel, Long> {
}
