package com.quick.recording.auth.service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoleDto {

    private UUID uuid;
    private String name;
    private List<PermissionDto> permissions;

}
