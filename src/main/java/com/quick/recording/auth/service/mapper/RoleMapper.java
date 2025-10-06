package com.quick.recording.auth.service.mapper;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.mapper.MainMapper;
import com.quick.recording.gateway.mapper.SmartMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class, SmartMapper.class})
public interface RoleMapper extends MainMapper<RoleEntity, RoleDto> {

}
