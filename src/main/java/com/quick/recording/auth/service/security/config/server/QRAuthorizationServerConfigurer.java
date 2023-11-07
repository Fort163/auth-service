package com.quick.recording.auth.service.security.config.server;

import com.quick.recording.auth.service.security.config.QRPrincipalUser;
import com.quick.recording.auth.service.dto.QRPrincipalUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenIntrospectionAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
@AllArgsConstructor
public class QRAuthorizationServerConfigurer {

    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    public void introspectionResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2TokenIntrospectionAuthenticationToken introspectionAuthenticationToken = (OAuth2TokenIntrospectionAuthenticationToken) authentication;
        if (introspectionAuthenticationToken.getTokenClaims().isActive()) {
            String token = introspectionAuthenticationToken.getToken();
            OAuth2Authorization tokenAuth = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
            Authentication attributeAuth = tokenAuth.getAttribute("java.security.Principal");
            if (attributeAuth != null) {
                if(attributeAuth.getPrincipal() instanceof QRPrincipalUser){
                    QRPrincipalUserDto qrPrincipalUserDto = new QRPrincipalUserDto((QRPrincipalUser) attributeAuth.getPrincipal());
                    ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
                    mappingJackson2HttpMessageConverter.write(qrPrincipalUserDto, null, httpResponse);
                }else {
                    throw new ClassCastException("Principal not supported " + attributeAuth.getPrincipal().getClass());
                }
            }
        }
        else {
            throw new AccessDeniedException("User for this token is blocked " + introspectionAuthenticationToken.getTokenClaims().getUsername());
        }
    }

}
