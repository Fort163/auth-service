package com.quick.recording.auth.service.security.util;

import com.quick.recording.auth.service.entity.BaseEntity;
import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.entity.UserEntity;
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
    private void init() {
        PermissionEntity read = createPermission("ROLE_READ");
        PermissionEntity write = createPermission("ROLE_WRITE");
        PermissionEntity create_space = createPermission("ROLE_CREATE_SPACE");

        RoleEntity simple_user = createRole("SIMPLE_USER");
        simple_user.setPermissions(List.of(read));
        write.setRoleList(List.of(simple_user));
        roleService.save(simple_user);


        RoleEntity space_admin = createRole("SPACE_ADMIN");
        RoleEntity service = createRole("SERVICE");
        service.setPermissionList(List.of(read,write));
        roleService.save(service);

        List<PermissionEntity> permissions = List.of(read, write, create_space);
        List<RoleEntity> roles = List.of(space_admin);
        space_admin.setPermissions(permissions);
        write.setRoleList(roles);
        read.setRoleList(roles);
        create_space.setRoleList(roles);
        roleService.save(space_admin);
        permissionService.save(read);
        permissionService.save(write);
        permissionService.save(create_space);


        UserEntity userCompany = createUserCompany();
        userCompany.setRoleList(List.of(service));
        userService.save(userCompany);

        UserEntity userUser = createUserUser();
        userUser.setRoleList(List.of(service));
        userService.save(userUser);

        UserEntity userTest = createUserTest();
        userTest.setRoleList(roles);
        userService.save(userTest);
    }

    private PermissionEntity createPermission(String permission) {
        PermissionEntity build = PermissionEntity.builder().permission(permission).build();
        return permissionService.save(build);
    }

    private RoleEntity createRole(String name) {
        RoleEntity build = RoleEntity.builder().name(name).build();
        return roleService.save(build);
    }

    private UserEntity createUserTest() {
        return UserEntity.builder()
                .birthDay(LocalDate.of(2000, 01, 01))
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

    private UserEntity createUserCompany(){
        return UserEntity.builder()
                .birthDay(LocalDate.of(2000,01,01))
                .email("company")
                .password(passwordEncoder.encode("company-service"))
                .username("company-service")
                .enabled(true)
                .fullName("company-service")
                .provider(AuthProvider.local)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .gender(Gender.NOT_DEFINED)
                .build();
    }

    private UserEntity createUserUser(){
        return UserEntity.builder()
                .birthDay(LocalDate.of(2000,01,01))
                .email("user")
                .password(passwordEncoder.encode("user-service"))
                .username("user-service")
                .enabled(true)
                .fullName("user-service")
                .provider(AuthProvider.local)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .gender(Gender.NOT_DEFINED)
                .build();
    }

    private <E extends BaseEntity> E addUuid(E obj) {
        obj.setUuid(UUID.randomUUID());
        return obj;
    }

}
