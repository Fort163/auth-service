package com.quick.recording.auth.service.security.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(value = "auth.permission", ignoreInvalidFields = true)
@Data
public class PermissionConfig {

    private List<String> include;
    private List<String> exclude;

}
