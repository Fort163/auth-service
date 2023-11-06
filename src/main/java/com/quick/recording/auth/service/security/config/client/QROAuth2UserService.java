package com.quick.recording.auth.service.security.config.client;

import com.quick.recording.auth.service.security.entity.UserEntity;
import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import com.quick.recording.auth.service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QROAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String clientRegId = userRequest.getClientRegistration().getRegistrationId();   // Получаем наименование провайдера (google, github и т.д.)
        AuthProvider provider = AuthProvider.valueOf(clientRegId);
        UserEntity save = userService.save(oAuth2User, provider);
        return oAuth2User;
    }

}
