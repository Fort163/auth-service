package com.quick.recording.auth.service.model;

import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import com.quick.recording.resource.service.enumeration.Gender;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class VkUser extends SocialUser {
    public VkUser(OAuth2User user) {
        super(user);
    }

    @Override
    public UserEntity getUserEntity() {
        return UserEntity.builder()
                .birthDay(getBirthDay())
                .credentialsNonExpired(true)
                .username((String)getAttribute("id"))
                .enabled(true)
                .firstName((String) getAttribute("first_name"))
                .lastName((String) getAttribute("last_name"))
                .fullName(getFullName())
                .gender(getGender())
                .userpic(getImageUrl())
                .phoneNumber((String) getAttribute("home_phone"))
                .providerId((String)getAttribute("id"))
                .provider(AuthProvider.vk)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .verified(true)
                .lastVisit(LocalDateTime.now())
                .build();
    }

    private String getFullName() {
        String fullName = "";
        String firstName = (String) getAttribute("first_name");
        String lastName = (String) getAttribute("last_name");
        if (Objects.nonNull(lastName)) {
            fullName = lastName + " ";
        }
        if (Objects.nonNull(firstName)) {
            fullName = fullName + firstName;
        }

        return fullName;
    }

    private Gender getGender() {
        Integer genderInteger = (Integer) getAttribute("sex");
        Gender gender = Gender.NOT_DEFINED;
        switch (genderInteger) {
            case 2 -> gender = Gender.MALE;
            case 1 -> gender = Gender.FEMALE;
        }
        return gender;
    }

    private String getImageUrl() {
        String avatar = (String) getAttribute("photo_max");
        if (Objects.nonNull(avatar) && !avatar.isEmpty()) {
            return avatar;
        }
        return null;
    }

    private LocalDate getBirthDay() {
        String birthDay = (String) getAttribute("bdate");
        if (Objects.nonNull(birthDay) && !birthDay.isEmpty()) {
            String[] date = birthDay.split("\\.");
            return LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
        }
        return null;
    }
}
