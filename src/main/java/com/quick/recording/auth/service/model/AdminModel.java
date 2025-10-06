package com.quick.recording.auth.service.model;

import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class AdminModel extends PageModel{

    private String mainSelect;
    private String securitySelect;
    private RoleDto roleSearch;
    private PermissionDto permissionSearch;
    private List<RoleDto> roleList;
    private List<PermissionDto> permissionList;

    public AdminModel() {
        this.roleSearch = new RoleDto();
        this.permissionSearch = new PermissionDto();
        this.roleList = new ArrayList<>();
        this.permissionList = new ArrayList<>();
    }
}
