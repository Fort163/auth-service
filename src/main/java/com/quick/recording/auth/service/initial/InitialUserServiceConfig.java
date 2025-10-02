package com.quick.recording.auth.service.initial;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.security.config.property.ServiceConfig;
import com.quick.recording.auth.service.service.UserService;
import com.quick.recording.gateway.config.MessageUtil;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import com.quick.recording.resource.service.enumeration.Gender;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Log4j2
@RequiredArgsConstructor
@Profile("local | docker")
@DependsOn("initialPermissionConfig")
public class InitialUserServiceConfig {

    private final EurekaClient discoveryClient;
    private final UserService userService;
    private final MessageUtil messageUtil;
    private final ServiceConfig serviceConfig;

    @PostConstruct
    private void createUserService(){
        Set<String> needCreateUserService = discoveryClient.getApplications()
                .getRegisteredApplications().stream()
                .map(Application::getName)
                .map(appName -> appName.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
        if(Objects.nonNull(serviceConfig) && Objects.nonNull(serviceConfig.getConfigs())) {
            needCreateUserService.addAll(serviceConfig.getConfigs().keySet());
        }
        userService.findAllByProvider(AuthProvider.service).stream()
                .map(UserEntity::getUsername)
                .collect(Collectors.toList())
                .forEach(needCreateUserService::remove);
        if(!needCreateUserService.isEmpty()){
            List<UserEntity> saveList = needCreateUserService.stream()
                    .peek(userName -> {
                        log.info(messageUtil.create(
                                "init.user.need.create",
                                userName));
                    })
                    .map(userName -> {
                return UserEntity.builder()
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .gender(Gender.NOT_DEFINED)
                        .provider(AuthProvider.service)
                        .birthDay(LocalDate.parse("2000-01-01"))
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .fullName(userName)
                        .username(userName)
                        .build();
                    })
                    .collect(Collectors.toList());
            userService.saveAll(saveList);
        }
    }

}
