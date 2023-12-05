package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.RoleEntity;

import java.util.UUID;

public interface RoleService {

    RoleEntity save(RoleEntity entity);

    RoleEntity findByName(String name);

    RoleEntity findByUuid(UUID uuid);
}
