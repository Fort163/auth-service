package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.RoleEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleEntity save(RoleEntity entity);

    RoleEntity findByName(String name);

    RoleEntity findByUuid(UUID uuid);

    List<RoleEntity> findAll();

    List<RoleEntity> saveAll(Collection<RoleEntity> list);
}
