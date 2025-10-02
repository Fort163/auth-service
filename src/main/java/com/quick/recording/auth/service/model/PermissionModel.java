package com.quick.recording.auth.service.model;

import com.quick.recording.gateway.dto.auth.PermissionDto;
import lombok.Data;

@Data
public class PermissionModel {

    private String permissionUUID;
    private PermissionDto currentModel;

}
