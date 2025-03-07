package com.quick.recording.auth.service.repository;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<UserEntity> findByUsernameAndProvider(String email, AuthProvider provider);

    Optional<UserEntity> findByUsername(String email);

    Boolean existsByUsername(String email);


    Boolean existsByProviderAndProviderId(AuthProvider provider, String providerId);

    Optional<UserEntity> findByProviderAndProviderId(AuthProvider provider, String providerId);

}
