package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.service.api.ApiPermissionService;
import com.quick.recording.auth.service.service.api.ApiRoleService;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import com.quick.recording.gateway.dto.auth.SearchRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ApiRoleService roleService;
    private final ApiPermissionService permissionService;


    @Override
    public List<RoleDto> searchRole(SearchRoleDto dto) {
        return roleService.findAll(dto, PageRequest.ofSize(1000)).getContent();
    }

    @Override
    public List<PermissionDto> searchPermission(SearchPermissionDto dto) {
        return permissionService.findAll(dto, PageRequest.ofSize(1000)).getContent();
    }
}
