package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.repository.UserRepository;
import com.quick.recording.auth.service.security.model.SocialUserFactory;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    @Override
    @CircuitBreaker(name = "database")
    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(messageUtil, UserEntity.class, id));
    }

    @Override
    @CircuitBreaker(name = "database")
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @CircuitBreaker(name = "database")
    public Optional<UserEntity> findByUsernameAndProvider(String username,AuthProvider provider) {
        return userRepository.findByUsernameAndProvider(username,provider);
    }

    @Override
    @CircuitBreaker(name = "database")
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @CircuitBreaker(name = "database")
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    @CircuitBreaker(name = "database")
    public UserEntity save(OAuth2User oAuth2User, AuthProvider provider) {
        UserEntity userEntity = SocialUserFactory.createSocialUser(oAuth2User, provider).getUserEntity();
        if(userRepository.existsByProviderAndProviderId(provider,userEntity.getProviderId())){
            Optional<UserEntity> optional = userRepository.findByProviderAndProviderId(provider,userEntity.getProviderId());
            if(optional.isPresent()){
                UserEntity existUser = optional.get();
                if(Objects.nonNull(userEntity.getBirthDay())){
                    existUser.setBirthDay(userEntity.getBirthDay());
                }
                if(Objects.nonNull(userEntity.getEmail())){
                    existUser.setEmail(userEntity.getEmail());
                }
                if(Objects.nonNull(userEntity.getUserpic())){
                    existUser.setUserpic(userEntity.getUserpic());
                }
                if(Objects.nonNull(userEntity.getPhoneNumber()) && Objects.isNull(existUser.getPhoneNumber())){
                    existUser.setPhoneNumber(userEntity.getPhoneNumber());
                }
                existUser.setLastVisit(LocalDateTime.now());
                return save(existUser);
            }
        }
        return save(userEntity);
    }
}
