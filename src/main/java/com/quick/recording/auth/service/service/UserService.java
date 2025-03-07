package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserEntity findById(UUID id);

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByUsernameAndProvider(String username, AuthProvider provider);

    Optional<UserEntity> findByEmail(String email);

    UserEntity save(UserEntity userEntity);

    UserEntity save(OAuth2User oAuth2User, AuthProvider provider);

}
