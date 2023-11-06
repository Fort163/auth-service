package com.quick.recording.auth.service.model;

import com.quick.recording.auth.service.exception.AuthenticationProcessingException;
import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class SocialUserFactory {

  public static SocialUser createSocialUser(OAuth2User oAuth2User,AuthProvider provider) {
    SocialUser user;
    switch (provider){
      case yandex -> user = new YandexUser(oAuth2User);
      default -> throw new AuthenticationProcessingException("This provider not supported : " + provider.name());
    }
    return user;
  }
}
