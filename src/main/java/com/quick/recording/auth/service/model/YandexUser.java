package com.quick.recording.auth.service.model;

import com.quick.recording.auth.service.security.entity.UserEntity;
import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import com.quick.recording.auth.service.security.enumeration.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class YandexUser extends SocialUser {

  public YandexUser(OAuth2User user) {
    super(user);
  }

  @Override
  public UserEntity getUserEntity() {
    return UserEntity.builder()
            .birthDay(
                    attributes.get("birthday") != null ?
                            LocalDate.parse((String)attributes.get("birthday"))
                             : null)
            .email((String)getAttribute("default_email"))
            //.password(passwordEncoder.encode("test"))
            .credentialsNonExpired(true)
            .username((String)getAttribute("display_name"))
            .enabled(true)
            .firstName((String)getAttribute("first_name"))
            .lastName((String)getAttribute("last_name"))
            .fullName(((String)getAttribute("real_name")))
            .gender(getGender())
            .userpic(getImageUrl())
            .phoneNumber(getPhone())
            .provider(AuthProvider.yandex)
            .accountNonLocked(true)
            .accountNonExpired(true)
            .verified(true)
            .lastVisit(LocalDateTime.now())
            .build();
  }

  private String getImageUrl() {
    String avatar = (String)getAttribute("default_avatar_id");
    if (Objects.nonNull(avatar) && !avatar.isEmpty()) {
      return "https://avatars.yandex.net/get-yapic/" + avatar + "/islands-75";
    }
    return null;
  }

  private Gender getGender() {
    String gender = (String)getAttribute("sex");
    if (Objects.nonNull(gender)) {
      return gender.equalsIgnoreCase("male") ? Gender.MALE : Gender.FEMALE;
    }
    return Gender.NOT_DEFINED;
  }

  private String getPhone() {
    Map phoneMap = (LinkedHashMap) getAttribute("default_phone");
    if (Objects.nonNull(phoneMap)) {
      return (String) phoneMap.get("number");
    }
    else return null;
  }


}
