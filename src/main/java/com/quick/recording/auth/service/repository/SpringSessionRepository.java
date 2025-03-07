package com.quick.recording.auth.service.repository;

import com.quick.recording.auth.service.entity.SpringSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SpringSessionRepository extends JpaRepository<SpringSessionEntity, String> {


    Optional<Set<SpringSessionEntity>> findAllByPrincipalNameAndSessionId(String principalName, String sessionId);

    Optional<Set<SpringSessionEntity>> findAllByPrincipalName(String principalName);

}
