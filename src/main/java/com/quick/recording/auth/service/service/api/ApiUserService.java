package com.quick.recording.auth.service.service.api;

import com.quick.recording.gateway.dto.auth.AuthUserDto;
import com.quick.recording.gateway.dto.auth.Role2UserDto;
import com.quick.recording.gateway.dto.auth.SearchUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ApiUserService {

    AuthUserDto byUuid(UUID uuid);

    Page<AuthUserDto> findAll(SearchUserDto searchUserDto, Pageable pageable);

    AuthUserDto patch(AuthUserDto user);

    AuthUserDto put(AuthUserDto user);

    Boolean delete(UUID uuid);

    Boolean restore(UUID uuid);

    Boolean addRole(Role2UserDto dto);
}
