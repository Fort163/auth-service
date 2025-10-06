package com.quick.recording.auth.service.mapper;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.mapper.MainMapper;
import com.quick.recording.gateway.mapper.SmartMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SmartMapper.class})
public interface PermissionMapper extends MainMapper<PermissionEntity, PermissionDto> {


}
