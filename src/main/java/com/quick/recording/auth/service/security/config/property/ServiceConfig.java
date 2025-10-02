package com.quick.recording.auth.service.security.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(value = "auth.service", ignoreInvalidFields = true)
@Data
public class ServiceConfig {

    private Map<String,List<String>> configs;

    public Map<String, List<String>> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, List<String>> configs) {
        this.configs = configs;
    }
}
