package com.quick.recording.auth.service.mapper;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.gateway.dto.auth.RoleDto;
import com.quick.recording.gateway.mapper.SmartMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class, SmartMapper.class})
public interface RoleMapper {

    RoleDto toRoleDto(RoleEntity entity);

    List<RoleDto> toRoleDtoList(List<RoleEntity> list);

    @Mapping(target = "isActive", ignore = true)
    RoleEntity toRoleEntity(RoleDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    RoleEntity toRoleEntityWithNull(RoleDto dto, @MappingTarget RoleEntity entity);

    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    RoleEntity toRoleEntity(RoleDto dto, @MappingTarget RoleEntity entity);

}
