package com.quick.recording.auth.service.mapper;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.model.UserRegistrationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUserEntity(UserRegistrationModel model);

}
