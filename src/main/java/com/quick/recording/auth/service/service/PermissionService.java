package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.PermissionEntity;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    PermissionEntity save(PermissionEntity entity);

    List<PermissionEntity> findAllByUuids(List<UUID> uuids);

    List<PermissionEntity> saveAll(List<PermissionEntity> permissions);
}
