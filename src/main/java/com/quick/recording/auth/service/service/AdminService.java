package com.quick.recording.auth.service.service;

import com.quick.recording.gateway.dto.SmartDto;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminService {

    Page<RoleDto> searchRole(RoleDto dto, Pageable pageable);

    Page<PermissionDto> searchPermission(PermissionDto dto, Pageable pageable);

    RoleDto getRoleByUUID(UUID uuid);

    PermissionDto getPermissionByUUID(UUID uuid);

    RoleDto persistRole(RoleDto dto);

    Boolean changeRoleActive(SmartDto dto);

    PermissionDto persistPermission(PermissionDto dto);

    Boolean changePermissionActive(SmartDto dto);

}
