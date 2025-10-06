package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
import com.quick.recording.gateway.dto.auth.Role2UserDto;
import com.quick.recording.gateway.main.service.local.MainService;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends MainService<UserEntity, AuthUserDto> {

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByUsernameAndProvider(String username, AuthProvider provider);

    UserEntity save(OAuth2User oAuth2User, AuthProvider provider);

    List<UserEntity> findAllByProvider(AuthProvider provider);

    Boolean addRole(Role2UserDto dto);

}
