package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.main.service.local.MainService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PermissionService extends MainService<PermissionEntity, PermissionDto> {

    List<PermissionEntity> findAllByUuids(List<UUID> uuids);

    List<PermissionEntity> saveAll(Collection<PermissionEntity> permissions);

    List<PermissionEntity> findAll();

    List<PermissionEntity> findAllByPermission(Collection<String> permissions);
}
