package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.exception.NotFoundException;
import com.quick.recording.auth.service.repository.UserRepository;
import com.quick.recording.auth.service.security.entity.UserEntity;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserEntity findById(UUID id) {
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserEntity.class, id));
  }

  public UserEntity save(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }
}
