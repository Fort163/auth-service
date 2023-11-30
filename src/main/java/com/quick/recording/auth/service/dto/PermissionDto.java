package com.quick.recording.auth.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PermissionDto {

    private UUID uuid;
    private String permission;

}
