package com.quick.recording.auth.service.model;

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

  public String getAttribute(String attribute) {
    return (String) attributes.get(attribute);
  }

  public abstract String getId();

  public abstract String getLogin();

  public abstract String getFirstName();

  public abstract String getLastName();

  public abstract String getEmail();

  public abstract String getImageUrl();

  public abstract Gender getGender();

  public abstract LocalDate getBirthday();

  public abstract String getPhone();

  public abstract String getLocale();
}
