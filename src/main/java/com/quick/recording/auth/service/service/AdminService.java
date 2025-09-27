package com.quick.recording.auth.service.service;

import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import com.quick.recording.gateway.dto.auth.SearchRoleDto;

import java.util.List;

public interface AdminService {

    List<RoleDto> searchRole(SearchRoleDto dto);

    List<PermissionDto> searchPermission(SearchPermissionDto dto);

}
