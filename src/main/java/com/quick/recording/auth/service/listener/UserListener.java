package com.quick.recording.auth.service.listener;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.service.RoleService;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserListener {

    private static RoleService roleService;

    private final String SIMPLE_USER_ROLE = "SIMPLE_USER";

    @Autowired
    public void init(RoleService roleService) {
        this.roleService = roleService;
    }

    @PrePersist
    private void prePersist(UserEntity user) {
        if (Objects.isNull(user.getRoleList())) {
            RoleEntity role = roleService.findByName(SIMPLE_USER_ROLE);
            user.setRoleList(List.of(role));
        }
    }

}
