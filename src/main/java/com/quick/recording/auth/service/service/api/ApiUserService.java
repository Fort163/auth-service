package com.quick.recording.auth.service.service.api;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.mapper.UserMapper;
import com.quick.recording.auth.service.repository.api.ApiUserRepository;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.gateway.dto.auth.SearchUserDto;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
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
public class ApiUserService {

    private final ApiUserRepository apiUserRepository;
    private final UserMapper userMapper;

    public AuthUserDto byUuid(UUID uuid){
        Assert.notNull(uuid, "Uuid cannot be null");
        UserEntity userEntity = apiUserRepository.findById(uuid).orElseThrow(() -> new NotFoundException(UserEntity.class, uuid));
        return userMapper.toUserDto(userEntity);
    }

    public Page<AuthUserDto> findAll(SearchUserDto searchUserDto, Pageable pageable) {
        List<UserEntity> list = apiUserRepository.searchUser(searchUserDto, pageable);
        long count = apiUserRepository.searchUserCount(searchUserDto);
        List<AuthUserDto> result = userMapper.toUserDtoList(list);
        return new PageImpl<AuthUserDto>(result, pageable, count);
    }

    public AuthUserDto patch(AuthUserDto user) {
        Assert.notNull(user.getUuid(), "Uuid cannot be null");
        UserEntity userEntity = apiUserRepository.findById(user.getUuid()).orElseThrow(() -> new NotFoundException(UserEntity.class, user.getUuid()));
        userEntity = userMapper.toUserEntityWithNull(user,userEntity);
        return userMapper.toUserDto(apiUserRepository.save(userEntity));
    }

    public AuthUserDto put(AuthUserDto user) {
        Assert.notNull(user.getUuid(), "Uuid cannot be null");
        UserEntity userEntity = apiUserRepository.findById(user.getUuid()).orElseThrow(() -> new NotFoundException(UserEntity.class, user.getUuid()));
        userEntity = userMapper.toUserEntity(user,userEntity);
        return userMapper.toUserDto(apiUserRepository.save(userEntity));
    }

    public Boolean delete(UUID uuid) {
        Assert.notNull(uuid, "Uuid cannot be null");
        UserEntity userEntity = apiUserRepository.findById(uuid).orElseThrow(() -> new NotFoundException(UserEntity.class, uuid));
        userEntity.setEnabled(false);
        apiUserRepository.save(userEntity);
        return true;
    }
}
