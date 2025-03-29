package com.quick.recording.auth.service.security.config;

import com.quick.recording.auth.service.security.config.property.AuthCorsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final AuthCorsConfig corsConfig;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(corsConfig.getAllowCredentials());
        config.setAllowPrivateNetwork(corsConfig.getAllowPrivateNetwork());
        config.addAllowedOrigin(convertList(corsConfig.getAllowedOrigins()));
        config.addAllowedHeader(convertList(corsConfig.getAllowedHeaders()));
        config.addExposedHeader(convertList(corsConfig.getExposedHeaders()));
        config.addAllowedMethod(convertList(corsConfig.getAllowedMethods()));
        config.setMaxAge(corsConfig.getMaxAge());
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Nullable
    private String convertList(@Nullable List<String> list){
        if(Objects.isNull(list) || list.isEmpty()){
            return null;
        }
        if(list.size() == 1 && list.get(0).equals(CorsConfiguration.ALL)){
            return CorsConfiguration.ALL;
        }
        return String.join(",",list);
    }

}
