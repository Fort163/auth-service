package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.exception.NotFoundException;
import com.quick.recording.auth.service.model.SocialUserFactory;
import com.quick.recording.auth.service.repository.UserRepository;
import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserEntity.class, id));
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity save(OAuth2User oAuth2User, AuthProvider provider) {
        UserEntity userEntity = SocialUserFactory.createSocialUser(oAuth2User, provider).getUserEntity();
        if(userRepository.existsByEmail(userEntity.getEmail())){
            Optional<UserEntity> optional = userRepository.findByEmail(userEntity.getEmail());
            if(optional.isPresent()){
                UserEntity existUser = optional.get();
                existUser.setBirthDay(userEntity.getBirthDay());
                existUser.setUsername(userEntity.getUsername());
                existUser.setEmail(userEntity.getEmail());
                existUser.setUserpic(userEntity.getUserpic());
                existUser.setFullName(userEntity.getFullName());
                existUser.setGender(userEntity.getGender());
                existUser.setLocale(userEntity.getLocale());
                existUser.setPhoneNumber(userEntity.getPhoneNumber());
                existUser.setLastVisit(LocalDateTime.now());
                existUser.setFirstName(userEntity.getFirstName());
                existUser.setLastName(userEntity.getLastName());
                return save(existUser);
            }
        }
        return save(userEntity);
    }
}
