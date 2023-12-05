package com.quick.recording.auth.service.service.api;

import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.dto.auth.SearchRoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ApiRoleService {

    RoleDto byUuid(UUID uuid);

    Page<RoleDto> findAll(SearchRoleDto searchRoleDto, Pageable pageable);

    RoleDto post(RoleDto role);

    RoleDto patch(RoleDto role);

    RoleDto put(RoleDto role);

    Boolean delete(UUID uuid);

}
