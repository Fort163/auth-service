package com.quick.recording.auth.service.repository;

import com.quick.recording.auth.service.entity.SpringSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringSessionRepository extends JpaRepository<SpringSessionEntity, String> {

    Optional<SpringSessionEntity> findByPrincipalName(String principalName);

}
