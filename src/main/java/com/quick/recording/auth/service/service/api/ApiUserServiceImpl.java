package com.quick.recording.auth.service.service.api;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.mapper.UserMapper;
import com.quick.recording.auth.service.repository.api.ApiUserRepository;
import com.quick.recording.auth.service.service.RoleService;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
import com.quick.recording.gateway.dto.auth.Role2UserDto;
import com.quick.recording.gateway.dto.auth.SearchUserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiUserServiceImpl implements ApiUserService {

    private final ApiUserRepository apiUserRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final MessageUtil messageUtil;

    @Override
    @CircuitBreaker(name = "database")
    public AuthUserDto byUuid(UUID uuid) {
        Assert.notNull(uuid, "Uuid cannot be null");
        UserEntity userEntity = apiUserRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException(messageUtil, UserEntity.class, uuid)
        );
        return userMapper.toUserDto(userEntity);
    }

    @Override
    @CircuitBreaker(name = "database")
    public Page<AuthUserDto> findAll(SearchUserDto searchUserDto, Pageable pageable) {
        List<UserEntity> list = apiUserRepository.searchUser(searchUserDto, pageable);
        long count = apiUserRepository.searchUserCount(searchUserDto);
        List<AuthUserDto> result = userMapper.toUserDtoList(list);
        return new PageImpl<AuthUserDto>(result, pageable, count);
    }

    @Override
    @CircuitBreaker(name = "database")
    public AuthUserDto patch(AuthUserDto user) {
        Assert.notNull(user.getUuid(), "Uuid cannot be null");
        UserEntity userEntity = apiUserRepository.findById(user.getUuid()).orElseThrow(
                () -> new NotFoundException(messageUtil, UserEntity.class, user.getUuid())
        );
        userEntity = userMapper.toUserEntityWithNull(user, userEntity);
        return userMapper.toUserDto(apiUserRepository.save(userEntity));
    }

    @Override
    @CircuitBreaker(name = "database")
    public AuthUserDto put(AuthUserDto user) {
        Assert.notNull(user.getUuid(), "Uuid cannot be null");
        UserEntity userEntity = apiUserRepository.findById(user.getUuid()).orElseThrow(
                () -> new NotFoundException(messageUtil, UserEntity.class, user.getUuid())
        );
        userEntity = userMapper.toUserEntity(user, userEntity);
        return userMapper.toUserDto(apiUserRepository.save(userEntity));
    }

    @Override
    @CircuitBreaker(name = "database")
    public Boolean delete(UUID uuid) {
        Assert.notNull(uuid, "Uuid cannot be null");
        UserEntity userEntity = apiUserRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException(messageUtil, UserEntity.class, uuid)
        );
        userEntity.setEnabled(false);
        apiUserRepository.save(userEntity);
        return true;
    }

    @Override
    @CircuitBreaker(name = "database")
    public Boolean addRole(Role2UserDto dto) {
        Assert.notNull(dto.getUser(), "Uuid user cannot be null");
        Assert.notNull(dto.getRole(), "Uuid role cannot be null");
        RoleEntity roleEntity = roleService.findByUuid(dto.getRole());
        UserEntity userEntity = apiUserRepository.findById(dto.getUser()).orElseThrow(
                () -> new NotFoundException(messageUtil, UserEntity.class, dto.getUser())
        );
        userEntity.getRoleList().add(roleEntity);
        apiUserRepository.save(userEntity);
        return true;
    }
}
