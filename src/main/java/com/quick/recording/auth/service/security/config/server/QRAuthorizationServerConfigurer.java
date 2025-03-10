package com.quick.recording.auth.service.security.config.server;

import com.quick.recording.auth.service.dto.QRPrincipalUserDto;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.security.config.QRPrincipalUser;
import com.quick.recording.auth.service.service.UserService;
import com.quick.recording.gateway.config.error.exeption.NotFoundException;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenIntrospectionAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QRAuthorizationServerConfigurer {

    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
    private final OAuth2AuthorizationService oAuth2AuthorizationService;
    private final UserService userService;

    public void introspectionResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2TokenIntrospectionAuthenticationToken introspectionAuthenticationToken = (OAuth2TokenIntrospectionAuthenticationToken) authentication;
        if (introspectionAuthenticationToken.getTokenClaims().isActive()) {
            String token = introspectionAuthenticationToken.getToken();
            OAuth2Authorization tokenAuth = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
            Authentication attributeAuth = tokenAuth.getAttribute("java.security.Principal");
            if (attributeAuth != null) {
                if (attributeAuth.getPrincipal() instanceof QRPrincipalUser) {
                    QRPrincipalUserDto qrPrincipalUserDto = new QRPrincipalUserDto((QRPrincipalUser) attributeAuth.getPrincipal());
                    ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
                    mappingJackson2HttpMessageConverter.write(qrPrincipalUserDto, null, httpResponse);
                } else {
                    throw new ClassCastException("Principal not supported " + attributeAuth.getPrincipal().getClass());
                }
            } else {
                if (Strings.isNotEmpty(request.getHeader("username"))) {
                    Optional<UserEntity> user = userService.findByUsernameAndProvider(request.getHeader("username"), AuthProvider.service);
                    if (user.isPresent()) {
                        UserEntity userEntity = user.get();
                        if (userEntity.getProvider().equals(AuthProvider.service)) {
                            QRPrincipalUserDto qrPrincipalUserDto = new QRPrincipalUserDto(new QRPrincipalUser(userEntity));
                            ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
                            mappingJackson2HttpMessageConverter.write(qrPrincipalUserDto, null, httpResponse);
                        } else {
                            throw new AccessDeniedException("User with email " + request.getHeader("username") + " have provider not service access denied");
                        }
                    } else {
                        throw new NotFoundException("User with email " + request.getHeader("username") + " not found");
                    }
                }
            }
        } else {
            throw new AccessDeniedException("User for this token is blocked " + introspectionAuthenticationToken.getTokenClaims().getUsername());
        }
    }


}
