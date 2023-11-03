package com.quick.recording.auth.service.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomHttpConfigurer extends AbstractHttpConfigurer<CustomHttpConfigurer, HttpSecurity> {

  private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
  @Override
  public void init(HttpSecurity http) throws Exception {
    http.oauth2Login(oauth -> oauth.successHandler(authenticationSuccessHandler));
  }
}
