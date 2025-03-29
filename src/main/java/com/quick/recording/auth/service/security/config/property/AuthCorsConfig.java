package com.quick.recording.auth.service.security.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(value = "auth.cors", ignoreInvalidFields = true)
@Data
public class AuthCorsConfig {

    @Nullable
    private List<String> allowedOrigins;
    @Nullable
    private List<String> allowedMethods;
    @Nullable
    private List<String> allowedHeaders;
    @Nullable
    private List<String> exposedHeaders;
    @Nullable
    private Boolean allowCredentials;
    @Nullable
    private Boolean allowPrivateNetwork;
    @Nullable
    private Long maxAge;

}
