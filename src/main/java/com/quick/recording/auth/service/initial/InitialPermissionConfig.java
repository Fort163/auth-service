package com.quick.recording.auth.service.initial;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.security.config.property.PermissionConfig;
import com.quick.recording.auth.service.service.PermissionService;
import com.quick.recording.auth.service.service.RoleService;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.gateway.dto.BaseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@Log4j2
@RequiredArgsConstructor
@Profile("local | docker")
public class InitialPermissionConfig {

    protected final String PERMISSION_TEMPLATE = "%s_%s";
    protected final String PERMISSION_POSTFIX_ALL = "ALL";
    protected final String PERMISSION_POSTFIX_READ = "READ";
    protected final String PERMISSION_POSTFIX_CREATE = "CREATE";
    protected final String PERMISSION_POSTFIX_EDIT = "EDIT";
    protected final String PERMISSION_POSTFIX_DELETE = "DELETE";

    private static final String PACKAGE_DTO = "com.quick.recording.gateway.dto";
    private final PermissionService permissionService;
    private final PermissionConfig permissionConfig;
    private final MessageUtil messageUtil;

    @PostConstruct
    private void createPermission(){
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(BaseDto.class));
        List<String> exclude = Collections.emptyList();
        List<String> include = Collections.emptyList();
        if(Objects.nonNull(permissionConfig)) {
            if(Objects.nonNull(permissionConfig.getExclude())) {
                exclude = permissionConfig.getExclude().stream()
                        .map(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE::translate)
                        .map(permission -> permission.toUpperCase(Locale.ROOT))
                        .collect(Collectors.toList());
            }
            if(Objects.nonNull(permissionConfig.getInclude())) {
                include = permissionConfig.getInclude().stream()
                        .map(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE::translate)
                        .map(permission -> permission.toUpperCase(Locale.ROOT))
                        .collect(Collectors.toList());
            }
        }
        final List<String> excludePermissions = exclude;
        List<String> permissionResources = provider.findCandidateComponents(PACKAGE_DTO).stream()
                .map(candidate -> candidate.getBeanClassName().split("\\."))
                .map(splitClassName -> splitClassName[splitClassName.length - 1])
                .filter(className -> className.endsWith("Dto"))
                .map(className -> className.substring(0, className.length() - 3))
                .map(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE::translate)
                .map(className -> className.toUpperCase(Locale.ROOT))
                .filter(permissionResource -> !excludePermissions.contains(permissionResource))
                .collect(Collectors.toList());
        permissionResources.addAll(include);
        List<String> needPermissionList = new ArrayList<>();
        permissionResources.forEach(permission -> createPermissionByName(permission, needPermissionList));
        List<PermissionEntity> allPermissionEntities = permissionService.findAll();
        List<String> findPermissionList = allPermissionEntities.stream()
                .map(PermissionEntity::getPermission)
                .collect(Collectors.toList());
        needPermissionList.removeAll(findPermissionList);
        if(!needPermissionList.isEmpty()){
            List<PermissionEntity> permissionForCreate = needPermissionList.stream()
                    .peek(permission -> {
                        log.info(messageUtil.create(
                                "init.permission.need.create",
                                permission));
                    })
                    .map(permission -> PermissionEntity.builder().permission(permission).isActive(true).build())
                    .collect(Collectors.toList());
            permissionService.saveAll(permissionForCreate);
        }
    }

    private void createPermissionByName(String permissionName, List<String> result){
        result.add(String.format(PERMISSION_TEMPLATE,permissionName,PERMISSION_POSTFIX_ALL));
        result.add(String.format(PERMISSION_TEMPLATE,permissionName,PERMISSION_POSTFIX_READ));
        result.add(String.format(PERMISSION_TEMPLATE,permissionName,PERMISSION_POSTFIX_CREATE));
        result.add(String.format(PERMISSION_TEMPLATE,permissionName,PERMISSION_POSTFIX_EDIT));
        result.add(String.format(PERMISSION_TEMPLATE,permissionName,PERMISSION_POSTFIX_DELETE));
    }

}
