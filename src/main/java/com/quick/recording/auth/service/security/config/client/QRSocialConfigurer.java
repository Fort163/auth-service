package com.quick.recording.auth.service.security.config.client;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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
                oauth2Login.loginPage("/login").authorizationEndpoint()
                        .authorizationRequestRepository(authorizationRequestRepository)
                        .and()
                        .userInfoEndpoint()
                        .userService(this.auth2UserService)
                        .oidcUserService(this.auth2UserService);
                oauth2Login.tokenEndpoint().accessTokenResponseClient(accessTokenResponseClient());
            }
            if (this.successHandler != null) {
                oauth2Login.successHandler(this.successHandler);
            }
            if (this.failureHandler != null) {
                oauth2Login.failureHandler(this.failureHandler);
            }
        });
    }

    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient =
                new DefaultAuthorizationCodeTokenResponseClient();

        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter =
                new OAuth2AccessTokenResponseHttpMessageConverter();

        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new QRTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));

        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

        accessTokenResponseClient.setRestOperations(restTemplate);
        return accessTokenResponseClient;
    }

}
