package com.quick.recording.auth.service.service;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.main.service.local.MainService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface RoleService extends MainService<RoleEntity, RoleDto> {

    RoleEntity findByName(String name);

}
