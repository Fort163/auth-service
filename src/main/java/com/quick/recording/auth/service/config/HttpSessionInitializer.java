package com.quick.recording.auth.service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@EnableJdbcHttpSession
@RequiredArgsConstructor
public class HttpSessionInitializer extends AbstractHttpSessionApplicationInitializer {

}
