package com.quick.recording.auth.service.mapper;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.gateway.dto.auth.PermissionDto;
import com.quick.recording.gateway.mapper.SmartMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SmartMapper.class})
public interface PermissionMapper {

    PermissionDto toPermissionDto(PermissionEntity entity);

    List<PermissionDto> toPermissionDtoList(List<PermissionEntity> list);

    PermissionEntity toPermissionEntity(PermissionDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "isActive", ignore = true)
    PermissionEntity toPermissionEntityWithNull(PermissionDto dto, @MappingTarget PermissionEntity entity);

    @Mapping(target = "isActive", ignore = true)
    PermissionEntity toPermissionEntity(PermissionDto dto, @MappingTarget PermissionEntity entity);

}
