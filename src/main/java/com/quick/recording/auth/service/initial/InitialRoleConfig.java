package com.quick.recording.auth.service.initial;

import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.security.config.property.ServiceConfig;
import com.quick.recording.auth.service.service.PermissionService;
import com.quick.recording.auth.service.service.RoleService;
import com.quick.recording.auth.service.service.UserService;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@Log4j2
@RequiredArgsConstructor
@Profile("local | docker")
@DependsOn("initialUserServiceConfig")
public class InitialRoleConfig {

    private final ServiceConfig serviceConfig;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final UserService userService;
    private final MessageUtil messageUtil;

    @PostConstruct
    private void createRole(){
        List<UserEntity> allUserServiceWithoutRoles = userService.findAllByProvider(AuthProvider.service).stream()
                .filter(entity -> entity.getRoleList().isEmpty())
                .collect(Collectors.toList());
        Set<String> needCreateRole = allUserServiceWithoutRoles.stream()
                .map(UserEntity::getUsername)
                .collect(Collectors.toSet());
        if(Objects.nonNull(serviceConfig) && Objects.nonNull(serviceConfig.getConfigs())) {
            needCreateRole.addAll(serviceConfig.getConfigs().keySet());
        }
        roleService.findAll().stream()
                .map(RoleEntity::getName)
                .map(this::roleNameToServiceName)
                .collect(Collectors.toList())
                .forEach(needCreateRole::remove);
        if(!needCreateRole.isEmpty()){
            List<RoleEntity> saveList = needCreateRole.stream()
                .map(serviceName -> Pair.of(serviceNameToRoleName(serviceName),
                        permissionService.findAllByPermission(getPermissionFromConfig(serviceName)))
                )
                .peek(pairRole2Permission -> {
                    log.info(messageUtil.create(
                            "init.role.need.create",
                            pairRole2Permission.getKey()));
                })
                .map(pairRole2Permission -> {
                    return RoleEntity.builder()
                            .name(pairRole2Permission.getKey())
                            .isActive(true)
                            .permissions(pairRole2Permission.getValue())
                            .build();
                })
                .collect(Collectors.toList());
            List<RoleEntity> roleEntities = roleService.saveAll(saveList);
            allUserServiceWithoutRoles.forEach(userService -> {
                userService.getRoleList().addAll(roleEntities.stream()
                    .filter(roleEntity -> roleEntity.getName().equals(serviceNameToRoleName(userService.getUsername())))
                            .peek(roleEntity -> {
                                log.info(messageUtil.create(
                                        "init.user.add.role",
                                        roleEntity.getName(),
                                        userService.getUsername()));
                            })
                    .collect(Collectors.toList())
                );
            });
            userService.saveAll(allUserServiceWithoutRoles);
        }

    }

    private String serviceNameToRoleName(String serviceName){
        return serviceName.replace("-","_").toUpperCase(Locale.ROOT);
    }

    private String roleNameToServiceName(String roleName){
        return roleName.replace("_","-").toLowerCase(Locale.ROOT);
    }

    private List<String> getPermissionFromConfig(String serviceName){
        if(Objects.nonNull(serviceConfig) && Objects.nonNull(serviceConfig.getConfigs())) {
            return serviceConfig.getConfigs().get(serviceName);
        }
        return Collections.emptyList();
    }

}
