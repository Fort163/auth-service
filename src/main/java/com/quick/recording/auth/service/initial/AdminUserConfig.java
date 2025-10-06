package com.quick.recording.auth.service.initial;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.security.config.property.AuthAdminConfig;
import com.quick.recording.auth.service.service.PermissionService;
import com.quick.recording.auth.service.service.RoleService;
import com.quick.recording.auth.service.service.UserService;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import com.quick.recording.resource.service.enumeration.Gender;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Profile("local | docker")
public class AdminUserConfig {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final UserService userService;
    private final AuthAdminConfig config;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void createAdmin(){
        if(!userService.existsByUsername(config.getUserName())){
            RoleEntity admin = roleService.findByName("ADMIN");
            UserEntity userEntity = UserEntity.builder()
                    .username(config.getUserName())
                    .password(passwordEncoder.encode(config.getPassword()))
                    .firstName(config.getFirstName())
                    .lastName(config.getLastName())
                    .email(config.getEmail())
                    .credentialsNonExpired(true)
                    .accountNonLocked(true)
                    .accountNonExpired(true)
                    .isActive(true)
                    .emailVerified(true)
                    .roleList(List.of(admin))
                    .gender(Gender.NOT_DEFINED)
                    .provider(AuthProvider.local)
                    .build();
            userService.save(userEntity);
        }

    }

}
