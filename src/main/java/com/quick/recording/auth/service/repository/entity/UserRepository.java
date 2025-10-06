package com.quick.recording.auth.service.repository.entity;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsernameAndProvider(String email, AuthProvider provider);

    Boolean existsByUsername(String email);

    Boolean existsByProviderAndProviderId(AuthProvider provider, String providerId);

    Optional<UserEntity> findByProviderAndProviderId(AuthProvider provider, String providerId);

    List<UserEntity> findAllByProvider(AuthProvider provider);

}
