package com.quick.recording.auth.service.model;

import com.quick.recording.auth.service.security.enumeration.Gender;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class YandexUser extends SocialUser {

  public YandexUser(OAuth2User user) {
    super(user);
  }

  @Override
  public String getId() {
    return getAttribute("id");
  }

  @Override
  public String getLogin() {
    return getAttribute("login");
  }

  @Override
  public String getFirstName() {
    return getAttribute("first_name");
  }

  @Override
  public String getLastName() {
    return getAttribute("last_name");
  }

  @Override
  public String getEmail() {
    return getAttribute("default_email");
  }

  @Override
  public String getImageUrl() {
    String avatar = getAttribute("default_avatar_id");
    if (Objects.nonNull(avatar) && !avatar.isEmpty()) {
      return "https://avatars.yandex.net/get-yapic/" + avatar + "/islands-75";
    }
    return null;
  }

  @Override
  public Gender getGender() {
    String gender = getAttribute("sex");
    if (Objects.nonNull(gender)) {
      return gender.equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE;
    }
    return Gender.NOT_DEFINED;
  }

  @Override
  public LocalDate getBirthday() {
    String birthday = (String) attributes.get("birthday");
    if (birthday != null && !birthday.isEmpty()) {
      return LocalDate.parse(birthday);
    }
    return null;
  }

  @Override
  public String getPhone() {
    return null;
  }

  @Override
  public String getLocale() {
    return null;
  }

}
