package com.quick.recording.auth.service.service;

import com.quick.recording.gateway.dto.SmartDto;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import com.quick.recording.gateway.dto.auth.SearchRoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminService {

    Page<RoleDto> searchRole(SearchRoleDto dto, Pageable pageable);

    Page<PermissionDto> searchPermission(SearchPermissionDto dto, Pageable pageable);

    RoleDto getRoleByUUID(UUID uuid);

    PermissionDto getPermissionByUUID(UUID uuid);

    RoleDto persistRole(RoleDto dto);

    Boolean changeRoleActive(SmartDto dto);

    PermissionDto persistPermission(PermissionDto dto);

    Boolean changePermissionActive(SmartDto dto);

}
