package com.quick.recording.auth.service.security.util;

import com.quick.recording.auth.service.security.entity.BaseEntity;
import com.quick.recording.auth.service.security.entity.PermissionEntity;
import com.quick.recording.auth.service.security.entity.RoleEntity;
import com.quick.recording.auth.service.security.entity.UserEntity;
import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import com.quick.recording.auth.service.security.enumeration.Gender;
import com.quick.recording.auth.service.service.PermissionService;
import com.quick.recording.auth.service.service.RoleService;
import com.quick.recording.auth.service.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TestInit {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init(){
        PermissionEntity test_read = createPermission("TEST_READ");
        PermissionEntity test_write = createPermission("TEST_WRITE");
        PermissionEntity test_create_space = createPermission("TEST_CREATE_SPACE");
        RoleEntity test_space_admin = createRole("TEST_SPACE_ADMIN");
        List<PermissionEntity> permissions = List.of(test_read, test_write, test_create_space);
        List<RoleEntity> roles = List.of(test_space_admin);
        test_space_admin.setPermissionList(permissions);
        test_write.setRoleList(roles);
        test_read.setRoleList(roles);
        test_create_space.setRoleList(roles);
        roleService.save(test_space_admin);
        permissionService.save(test_read);
        permissionService.save(test_write);
        permissionService.save(test_create_space);
        UserEntity userTest = createUserTest();
        userTest.setRoleList(roles);
        userService.save(userTest);
    }

    private PermissionEntity createPermission(String permission){
        PermissionEntity build = PermissionEntity.builder().permission(permission).build();
        return permissionService.save(build);
    }

    private RoleEntity createRole(String name){
        RoleEntity build = RoleEntity.builder().name(name).build();
        return roleService.save(build);
    }

    private UserEntity createUserTest(){
        return UserEntity.builder()
                .birthDay(LocalDate.of(2000,01,01))
                .email("test@test.ru")
                .password(passwordEncoder.encode("test"))
                .username("test@test.ru")
                .enabled(true)
                .firstName("Тест")
                .lastName("Тестовый")
                .provider(AuthProvider.local)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .gender(Gender.NOT_DEFINED)
                .build();
    }

    private <E extends BaseEntity> E addUuid(E obj){
        obj.setUuid(UUID.randomUUID());
        return obj;
    }

}
