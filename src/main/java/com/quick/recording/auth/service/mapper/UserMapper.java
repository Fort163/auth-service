package com.quick.recording.auth.service.mapper;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.model.UserRegistrationModel;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUserEntity(UserRegistrationModel model);

    AuthUserDto toUserDto(UserEntity entity);

    List<AuthUserDto> toUserDtoList(List<UserEntity> list);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity toUserEntityWithNull(AuthUserDto dto, @MappingTarget UserEntity entity);


    UserEntity toUserEntity(AuthUserDto dto, @MappingTarget UserEntity entity);

}
