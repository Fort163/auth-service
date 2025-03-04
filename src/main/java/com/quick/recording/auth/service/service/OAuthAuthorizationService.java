package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.OAuthAuthorizationEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface OAuthAuthorizationService {

    Boolean existsByUuid(UUID uuid);

    Optional<OAuthAuthorizationEntity> findByUuid(UUID uuid);

    OAuthAuthorizationEntity save(OAuthAuthorizationEntity entity);

    Optional<OAuthAuthorizationEntity> findByToken(String token, String tokenType);

    void delete(UUID uuid);

}
