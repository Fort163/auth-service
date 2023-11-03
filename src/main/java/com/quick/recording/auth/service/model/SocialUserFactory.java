package com.quick.recording.auth.service.model;

import com.quick.recording.auth.service.exception.AuthenticationProcessingException;
import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class SocialUserFactory {

  public static SocialUser getOAuth2UserInfo(String registrationId, OAuth2User oAuth2User,String accessToken) {

    if(registrationId.equalsIgnoreCase(AuthProvider.YANDEX.getName())) {
      return new YandexUser(oAuth2User);
    } else if(registrationId.equalsIgnoreCase(AuthProvider.VK.getName())) {
      return null;
    } else {
      throw new AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
    }
  }
}
