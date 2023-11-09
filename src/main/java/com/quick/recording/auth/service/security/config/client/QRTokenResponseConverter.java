package com.quick.recording.auth.service.security.config.client;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QRTokenResponseConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

    @Override
    public OAuth2AccessTokenResponse convert(Map<String, Object> tokenResponseParameters) {
        return OAuth2AccessTokenResponse.withToken(tokenResponseParameters.get(OAuth2ParameterNames.ACCESS_TOKEN).toString())
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .additionalParameters(new HashMap<>(tokenResponseParameters))
                .build();
    }
}
