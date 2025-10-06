package com.quick.recording.auth.service.mapper;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.model.UserRegistrationModel;
import com.quick.recording.gateway.dto.auth.AuthUserDto;
import com.quick.recording.gateway.mapper.MainMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends MainMapper<UserEntity, AuthUserDto> {

    UserEntity toUserEntity(UserRegistrationModel model);

}
