package com.quick.recording.auth.service.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "spring.security.oauth2.authorization-server")
@Setter
@Getter
@Configuration
public class AuthorizationServerProperties {

    private String issuerUrl;
    private String introspectionEndpoint;

}
