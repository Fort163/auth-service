package com.quick.recording.auth.service.service;

import com.quick.recording.gateway.dto.SmartDto;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.enumerated.Delete;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RoleService roleService;
    private final PermissionService permissionService;


    @Override
    public Page<RoleDto> searchRole(RoleDto dto, Pageable pageable) {
        return roleService.search(dto, pageable);
    }

    @Override
    public Page<PermissionDto> searchPermission(PermissionDto dto, Pageable pageable) {
        return permissionService.search(dto, pageable);
    }

    @Override
    public RoleDto getRoleByUUID(UUID uuid) {
        return roleService.byUuid(uuid);
    }

    @Override
    public PermissionDto getPermissionByUUID(UUID uuid) {
        return permissionService.byUuid(uuid);
    }

    @Override
    public RoleDto persistRole(RoleDto dto) {
        if(Objects.isNull(dto.getUuid())){
            dto = roleService.post(dto);
        }
        else {
            dto = roleService.put(dto);
        }
        return dto;
    }

    @Override
    public Boolean changeRoleActive(SmartDto dto) {
        if(dto.getIsActive()){
            return roleService.restore(dto.getUuid());
        }
        else {
            return roleService.delete(dto.getUuid(), Delete.SOFT);
        }
    }

    @Override
    public PermissionDto persistPermission(PermissionDto dto) {
        if(Objects.isNull(dto.getUuid())){
            dto = permissionService.post(dto);
        }
        else {
            dto = permissionService.patch(dto);
        }
        return dto;
    }

    @Override
    public Boolean changePermissionActive(SmartDto dto) {
        if(dto.getIsActive()){
            return permissionService.restore(dto.getUuid());
        }
        else {
            return permissionService.delete(dto.getUuid(), Delete.SOFT);
        }
    }

}
