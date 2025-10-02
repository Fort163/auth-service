package com.quick.recording.auth.service.model;

import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.dto.auth.SearchPermissionDto;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoleModel extends PageModel {

    private String backPanel;
    private String roleUUID;
    private RoleDto currentModel;
    private List<UUID> currentPermissionUUIDs;
    private String addPermissionUUID;
    private String deletePermissionUUID;
    private SearchPermissionDto searchPermission;
    private List<PermissionDto> permissionList;

}
