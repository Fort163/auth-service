package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.exception.NotFoundException;
import com.quick.recording.auth.service.model.SocialUserFactory;
import com.quick.recording.auth.service.repository.UserRepository;
import com.quick.recording.auth.service.security.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserEntity findById(UUID id) {
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserEntity.class, id));
  }

  public Optional<UserEntity> findByEmail(String email){
    return userRepository.findByEmail(email);
  }

  public UserEntity save(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }

  public UserEntity save(OAuth2User oAuth2User, AuthProvider provider) {
    UserEntity userEntity = SocialUserFactory.createSocialUser(oAuth2User, provider).getUserEntity();
    return save(userEntity);
  }
}
