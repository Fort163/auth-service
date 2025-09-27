package com.quick.recording.auth.service.model;

import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import com.quick.recording.gateway.dto.auth.SearchRoleDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class AdminModel {

    private String mainSelect;
    private String securitySelect;
    private SearchRoleDto roleSearch;
    private SearchPermissionDto permissionSearch;
    private List<RoleDto> roleList;
    private List<PermissionDto> permissionList;
    private UUID selectRole;
    private UUID selectPermission;

    public AdminModel() {
        this.roleSearch = new SearchRoleDto();
        this.permissionSearch = new SearchPermissionDto();
        this.roleList = new ArrayList<>();
        this.permissionList = new ArrayList<>();
    }
}
