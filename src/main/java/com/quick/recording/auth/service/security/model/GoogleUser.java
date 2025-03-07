package com.quick.recording.auth.service.security.model;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import com.quick.recording.resource.service.enumeration.Gender;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Objects;

public class GoogleUser extends SocialUser {

    public GoogleUser(OAuth2User user) {
        super(user);
    }

    @Override
    public UserEntity getUserEntity() {
        return UserEntity.builder()
                //.birthDay(getBirthDay())
                .email((String) getAttribute("email"))
                .credentialsNonExpired(true)
                .username((String) getAttribute("sub"))
                .enabled(true)
                .firstName((String) getAttribute("given_name"))
                .lastName((String) getAttribute("family_name"))
                .gender(getGender())
                .userpic((String) getAttribute("picture"))
                //.phoneNumber(getPhone())
                .providerId((String) getAttribute("sub"))
                .provider(AuthProvider.google)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .lastVisit(LocalDateTime.now())
                .build();
    }

    private Gender getGender() {
        String gender = (String) getAttribute("sex");
        if (Objects.nonNull(gender)) {
            return "male".equalsIgnoreCase(gender) ? Gender.MALE : Gender.FEMALE;
        }
        return Gender.NOT_DEFINED;
    }

}
