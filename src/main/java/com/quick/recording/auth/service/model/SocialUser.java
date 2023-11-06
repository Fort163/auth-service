package com.quick.recording.auth.service.model;

import com.quick.recording.auth.service.security.entity.UserEntity;
import com.quick.recording.auth.service.security.enumeration.Gender;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public abstract class SocialUser {

  protected Map<String, Object> attributes;
  protected OAuth2User user;

  public SocialUser(OAuth2User user) {
    this.attributes = user.getAttributes();
    this.user = user;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public OAuth2User getUser(){return this.user;}

  public Object getAttribute(String attribute) {
    return attributes.get(attribute);
  }

  public abstract UserEntity getUserEntity();
}
