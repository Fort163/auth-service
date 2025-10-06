package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.mapper.UserMapper;
import com.quick.recording.auth.service.repository.entity.UserRepository;
import com.quick.recording.auth.service.security.model.SocialUserFactory;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
import com.quick.recording.gateway.dto.auth.Role2UserDto;
import com.quick.recording.gateway.main.service.local.CacheableMainServiceAbstract;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CacheableMainServiceAbstract<UserEntity, AuthUserDto, UserRepository, UserMapper>
        implements UserService {

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(RoleService roleService,
                           UserRepository repository,
                           UserMapper mapper,
                           MessageUtil messageUtil,
                           StreamBridge streamBridge) {
        super(repository, mapper, messageUtil, UserEntity.class, streamBridge);
        this.roleService = roleService;
    }

    @Override
    @CircuitBreaker(name = "database")
    public Boolean existsByUsername(String username) {
        return getRepository().existsByUsername(username);
    }

    @Override
    @CircuitBreaker(name = "database")
    public Optional<UserEntity> findByUsernameAndProvider(String username, AuthProvider provider) {
        return getRepository().findByUsernameAndProvider(username, provider);
    }

    @Override
    @CircuitBreaker(name = "database")
    public UserEntity save(OAuth2User oAuth2User, AuthProvider provider) {
        UserEntity userEntity = SocialUserFactory.createSocialUser(oAuth2User, provider).getUserEntity();
        if (getRepository().existsByProviderAndProviderId(provider, userEntity.getProviderId())) {
            Optional<UserEntity> optional = getRepository().findByProviderAndProviderId(provider, userEntity.getProviderId());
            if (optional.isPresent()) {
                UserEntity existUser = optional.get();
                if (Objects.nonNull(userEntity.getBirthDay())) {
                    existUser.setBirthDay(userEntity.getBirthDay());
                }
                if (Objects.nonNull(userEntity.getEmail())) {
                    existUser.setEmail(userEntity.getEmail());
                }
                if (Objects.nonNull(userEntity.getUserpic())) {
                    existUser.setUserpic(userEntity.getUserpic());
                }
                if (Objects.nonNull(userEntity.getPhoneNumber()) && Objects.isNull(existUser.getPhoneNumber())) {
                    existUser.setPhoneNumber(userEntity.getPhoneNumber());
                }
                existUser.setLastVisit(LocalDateTime.now());
                return save(existUser);
            }
        }
        return save(userEntity);
    }

    @Override
    @CircuitBreaker(name = "database")
    public List<UserEntity> findAllByProvider(AuthProvider provider) {
        return getRepository().findAllByProvider(provider);
    }

    @Override
    @CircuitBreaker(name = "database")
    public Boolean addRole(Role2UserDto dto) {
        Assert.notNull(dto.getUser(), "Uuid user cannot be null");
        Assert.notNull(dto.getRole(), "Uuid role cannot be null");
        RoleEntity roleEntity = roleService.byUuidEntity(dto.getRole());
        UserEntity userEntity = getRepository().findById(dto.getUser()).orElseThrow(
                () -> new NotFoundException(messageUtil, UserEntity.class, dto.getUser())
        );
        userEntity.getRoleList().add(roleEntity);
        getRepository().save(userEntity);
        return true;
    }

    @Override
    public ExampleMatcher prepareExampleMatcher(ExampleMatcher exampleMatcher) {
        return exampleMatcher;
    }

    @Override
    public Class<AuthUserDto> getType() {
        return AuthUserDto.class;
    }
}
