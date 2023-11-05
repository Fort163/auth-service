package com.quick.recording.auth.service.security.config.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QRAuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final HttpSessionOAuth2AuthorizationRequestRepository authorizationRequestRepository = new HttpSessionOAuth2AuthorizationRequestRepository();

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return this.authorizationRequestRepository.loadAuthorizationRequest(request);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        this.authorizationRequestRepository.saveAuthorizationRequest(authorizationRequest,request,response);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.authorizationRequestRepository.removeAuthorizationRequest(request,response);
    }
}
