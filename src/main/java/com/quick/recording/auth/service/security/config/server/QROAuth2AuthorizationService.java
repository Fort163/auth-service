package com.quick.recording.auth.service.security.config.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quick.recording.auth.service.entity.OAuthAuthorizationEntity;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.security.config.jacson.QROAuth2AuthorizationServerJackson2Module;
import com.quick.recording.auth.service.security.config.QRPrincipalUser;
import com.quick.recording.auth.service.service.OAuthAuthorizationService;
import com.quick.recording.auth.service.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.Module;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

@Component
@Primary
@RequiredArgsConstructor
public class QROAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final OAuthAuthorizationService oAuthAuthorizationService;
    private final UserService userService;
    private final RegisteredClientRepository registeredClientRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    private void init(){
        ClassLoader classLoader = QROAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);
        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        this.objectMapper.registerModule(new QROAuth2AuthorizationServerJackson2Module());
    }

    @SneakyThrows
    @Override
    public void save(OAuth2Authorization authorization) {
        OAuthAuthorizationEntity convert = this.convert(authorization);
        oAuthAuthorizationService.save(convert);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Optional<OAuthAuthorizationEntity> byId = oAuthAuthorizationService.findByUuid(UUID.fromString(authorization.getId()));
        if(byId.isPresent()) {
            oAuthAuthorizationService.delete(byId.get().getUuid());
        }
    }

    @SneakyThrows
    @Override
    public OAuth2Authorization findById(String id) {
        if(id != null) {
            Optional<OAuthAuthorizationEntity> byId = oAuthAuthorizationService.findByUuid(UUID.fromString(id));
            if(byId.isPresent()) {
                return convert(byId.get());
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        if(token != null) {
            String type = tokenType != null ? tokenType.getValue() : "notSet";
            Optional<OAuthAuthorizationEntity> byToken = oAuthAuthorizationService.findByToken(token, type);
            if(byToken.isPresent()) {
                return convert(byToken.get());
            }
        }
        return null;
    }

    private OAuthAuthorizationEntity convert(OAuth2Authorization oAuth2Authorization) throws JsonProcessingException {
        OAuthAuthorizationEntity entity;
        if(oAuthAuthorizationService.findByUuid(UUID.fromString(oAuth2Authorization.getId())).isPresent()){
            entity = oAuthAuthorizationService.findByUuid(UUID.fromString(oAuth2Authorization.getId())).get();
        }
        else {
            entity = new OAuthAuthorizationEntity();
            entity.setUuid(UUID.fromString(oAuth2Authorization.getId()));
            entity.setPrincipalName(oAuth2Authorization.getPrincipalName());
            entity.setUser(getUser(oAuth2Authorization));
            entity.setRegisteredClient(oAuth2Authorization.getRegisteredClientId());
        }
        entity.setGrantType(oAuth2Authorization.getAuthorizationGrantType().getValue());
        entity.setAttributes(this.objectMapper.writeValueAsString(oAuth2Authorization.getAttributes()));
        setTokenCode(oAuth2Authorization,entity);
        setTokenAccess(oAuth2Authorization,entity);
        setTokenRefresh(oAuth2Authorization,entity);
        return entity;
    }

    private OAuth2Authorization convert(OAuthAuthorizationEntity oAuthAuthorizationEntity) {
        RegisteredClient byId = registeredClientRepository.findById(oAuthAuthorizationEntity.getRegisteredClient());
        Map<Class<? extends OAuth2Token>, OAuth2Token> tokens = new HashMap<>();
        Map<String, Object> attributesVar = null;
        try {
            attributesVar = this.objectMapper.readValue(oAuthAuthorizationEntity.getAttributes(),
                            new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            //TODO
            e.printStackTrace();
            attributesVar = new HashMap<>();
        }
        Map<String, Object> attributes = attributesVar;
        OAuth2Authorization.Builder build = OAuth2Authorization.withRegisteredClient(byId)
                .id(oAuthAuthorizationEntity.getUuid().toString())
                .authorizationGrantType(new AuthorizationGrantType(oAuthAuthorizationEntity.getGrantType()))
                .principalName(oAuthAuthorizationEntity.getPrincipalName())
                .authorizedScopes(byId.getScopes());

        if(Objects.nonNull(oAuthAuthorizationEntity.getAuthorizationCodeValue())){
            build.token(new OAuth2AuthorizationCode(
                    oAuthAuthorizationEntity.getAuthorizationCodeValue(),
                    oAuthAuthorizationEntity.getAuthorizationCodeIssuedAt().toInstant(ZoneOffset.UTC),
                    oAuthAuthorizationEntity.getAuthorizationCodeExpiresAt().toInstant(ZoneOffset.UTC)
            ));
        }
        if(Objects.nonNull(oAuthAuthorizationEntity.getRefreshTokenValue())){
            build.token(new OAuth2RefreshToken(
                    oAuthAuthorizationEntity.getRefreshTokenValue(),
                    oAuthAuthorizationEntity.getRefreshTokenIssuedAt().toInstant(ZoneOffset.UTC),
                    oAuthAuthorizationEntity.getRefreshTokenExpiresAt().toInstant(ZoneOffset.UTC)
            ));
        }
        if(Objects.nonNull(oAuthAuthorizationEntity.getAccessTokenValue())){
            build.token(new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.BEARER,
                    oAuthAuthorizationEntity.getAccessTokenValue(),
                    oAuthAuthorizationEntity.getAccessTokenIssuedAt().toInstant(ZoneOffset.UTC),
                    oAuthAuthorizationEntity.getAccessTokenExpiresAt().toInstant(ZoneOffset.UTC)
            ));
        }

        attributes.keySet().stream().forEach(key -> {
            build.attribute(key,attributes.get(key));
        });
        return build.build();
    }

    private void setTokenCode(OAuth2Authorization oAuth2Authorization,OAuthAuthorizationEntity entity){
        if(Objects.nonNull(oAuth2Authorization.getToken(OAuth2AuthorizationCode.class))) {
            OAuth2AuthorizationCode token = oAuth2Authorization.getToken(OAuth2AuthorizationCode.class).getToken();
            if (Objects.nonNull(token)) {
                entity.setAuthorizationCodeValue(token.getTokenValue());
                entity.setAuthorizationCodeIssuedAt(LocalDateTime.ofInstant(token.getIssuedAt(), ZoneId.systemDefault()));
                entity.setAuthorizationCodeExpiresAt(LocalDateTime.ofInstant(token.getExpiresAt(), ZoneId.systemDefault()));
            }
        }
    }

    private void setTokenRefresh(OAuth2Authorization oAuth2Authorization,OAuthAuthorizationEntity entity){
        if(Objects.nonNull(oAuth2Authorization.getRefreshToken())) {
            OAuth2RefreshToken token = oAuth2Authorization.getRefreshToken().getToken();
            if (Objects.nonNull(token)) {
                entity.setRefreshTokenValue(token.getTokenValue());
                entity.setRefreshTokenIssuedAt(LocalDateTime.ofInstant(token.getIssuedAt(), ZoneId.systemDefault()));
                entity.setRefreshTokenExpiresAt(LocalDateTime.ofInstant(token.getExpiresAt(), ZoneId.systemDefault()));
            }
        }
    }

    private void setTokenAccess(OAuth2Authorization oAuth2Authorization,OAuthAuthorizationEntity entity){
        if(Objects.nonNull(oAuth2Authorization.getAccessToken())) {
            OAuth2AccessToken token = oAuth2Authorization.getAccessToken().getToken();
            if (Objects.nonNull(token)) {
                entity.setAccessTokenType(token.getTokenType().getValue());
                entity.setAccessTokenValue(token.getTokenValue());
                entity.setAccessTokenIssuedAt(LocalDateTime.ofInstant(token.getIssuedAt(), ZoneId.systemDefault()));
                entity.setAccessTokenExpiresAt(LocalDateTime.ofInstant(token.getExpiresAt(), ZoneId.systemDefault()));
            }
        }
    }

    private UserEntity getUser(OAuth2Authorization oAuth2Authorization){
        Authentication attributeAuth = oAuth2Authorization.getAttribute("java.security.Principal");
        if(Objects.nonNull(attributeAuth)) {
            QRPrincipalUser principal = (QRPrincipalUser) attributeAuth.getPrincipal();
            return userService.findByUsernameAndProvider(principal.getUsername(),principal.getProvider()).orElseThrow();
        }
        return null;
    }

}
