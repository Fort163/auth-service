package com.quick.recording.auth.service.security.config;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import com.quick.recording.auth.service.security.enumeration.Gender;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Getter
public class QRPrincipalUser implements OAuth2User, Principal, UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private String name;
    private String fullName;
    private String userpic;
    private String email;
    private String locale;
    private Gender gender;
    private AuthProvider provider;
    private String phoneNumber;
    private LocalDate birthDay;
    private String password;
    private String username;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
    private Boolean enabled;

    public QRPrincipalUser(UserEntity user) {
        this.name = user.getUsername();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.userpic = user.getUserpic();
        this.email = user.getEmail();
        this.locale = user.getLocale();
        this.gender = user.getGender();
        this.provider = user.getProvider();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDay = user.getBirthDay();
        this.authorities = setAuthorities(user);
        this.password = user.getPassword();
        this.accountNonExpired = user.getAccountNonExpired();
        this.credentialsNonExpired = user.getCredentialsNonExpired();
        this.accountNonLocked = user.getAccountNonLocked();
        this.enabled = user.getEnabled();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    private Collection<? extends GrantedAuthority> setAuthorities(UserEntity user) {
        return user.getRoleList().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(PermissionEntity::getPermission)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

}
