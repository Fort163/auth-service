package com.quick.recording.auth.service.repository;

import com.quick.recording.auth.service.entity.OAuthAuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OAuthAuthorizationRepository extends JpaRepository<OAuthAuthorizationEntity, UUID> {

    Boolean existsByUuid(UUID uuid);

    Optional<OAuthAuthorizationEntity> findByUuid(UUID uuid);

    Optional<OAuthAuthorizationEntity> findByOidcIdTokenValue(String token);

    Optional<OAuthAuthorizationEntity> findByAuthorizationCodeValue(String token);

    Optional<OAuthAuthorizationEntity> findByState(String token);

    Optional<OAuthAuthorizationEntity> findByAccessTokenValue(String token);

    Optional<OAuthAuthorizationEntity> findByRefreshTokenValue(String token);

    Optional<OAuthAuthorizationEntity> findByAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrState(String authorizationCodeValue, String accessTokenValue, String refreshTokenValue, String state);

}
