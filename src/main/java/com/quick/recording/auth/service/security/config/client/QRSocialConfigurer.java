package com.quick.recording.auth.service.security.config.client;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QRSocialConfigurer extends AbstractHttpConfigurer<QRSocialConfigurer, HttpSecurity> {


    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthorizationRequestRepository authorizationRequestRepository;
    private final OAuth2UserService auth2UserService;


    @Override
    public void init(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2Login -> {
            if (this.auth2UserService != null) {
                oauth2Login.authorizationEndpoint()
                        .authorizationRequestRepository(authorizationRequestRepository)
                        .and()
                        .userInfoEndpoint()
                        .userService(this.auth2UserService);
            }
            if (this.successHandler != null) {
                oauth2Login.successHandler(this.successHandler);
            }
            if (this.failureHandler != null) {
                oauth2Login.failureHandler(this.failureHandler);
            }
        });
    }

}
