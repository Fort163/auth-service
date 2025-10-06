package com.quick.recording.auth.service;

import com.quick.recording.gateway.config.cache.CacheStreamConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication(scanBasePackages = {"com.quick.recording"}, exclude = {RedisAutoConfiguration.class})
@EnableWebSecurity
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.quick.recording.gateway.service")
@EnableJpaRepositories
@RefreshScope
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(AuthServiceApplication.class).build(args);
        application.addInitializers(new CacheStreamConfigurer());
        application.run();
    }

}
