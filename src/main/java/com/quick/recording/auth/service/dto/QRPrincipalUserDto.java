package com.quick.recording.auth.service.dto;

import com.quick.recording.auth.service.security.config.QRPrincipalUser;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import com.quick.recording.resource.service.enumeration.Gender;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Data
public class QRPrincipalUserDto {

    private Collection<? extends GrantedAuthority> authorities;
    private UUID uuid;
    private String name;
    private String fullName;
    private String userpic;
    private String email;
    private String locale;
    private AuthProvider provider;
    private Gender gender;
    private String phoneNumber;
    private LocalDate birthDay;
    private Boolean active;

    public QRPrincipalUserDto(QRPrincipalUser user) {
        this.uuid = user.getUuid();
        this.name = user.getUsername();
        this.fullName = user.getFullName();
        this.userpic = user.getUserpic();
        this.email = user.getEmail();
        this.locale = user.getLocale();
        this.gender = user.getGenderEnum();
        this.provider = user.getProvider();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDay = user.getBirthDay();
        this.authorities = user.getAuthorities();
        this.active = user.isEnabled();
    }

}
