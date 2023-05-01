package com.dizertatie.reactiveinvestigations.repository;

import com.dizertatie.reactiveinvestigations.persistance.model.TargetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepo extends JpaRepository<TargetModel, Long> {
}
