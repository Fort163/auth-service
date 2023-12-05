package com.quick.recording.auth.service.security.config;

import com.quick.recording.auth.service.entity.PermissionEntity;
import com.quick.recording.auth.service.entity.RoleEntity;
import com.quick.recording.auth.service.entity.UserEntity;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import com.quick.recording.resource.service.enumeration.Gender;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Getter
public class QRPrincipalUser implements OAuth2User, OidcUser, Principal, UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private UUID uuid;
    private String name;
    private String fullName;
    private String userpic;
    private String email;
    private String locale;
    private Gender genderEnum;
    private AuthProvider provider;
    private String phoneNumber;
    private LocalDate birthDay;
    private String password;
    private String username;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
    private Boolean enabled;

    @Builder
    public QRPrincipalUser(Collection<? extends GrantedAuthority> authorities,UUID uuid, String name, String fullName, String userpic, String email, String locale, Gender genderEnum, AuthProvider provider, String phoneNumber, LocalDate birthDay, String password, String username, Boolean accountNonExpired, Boolean credentialsNonExpired, Boolean accountNonLocked, Boolean enabled) {
        this.uuid = uuid;
        this.authorities = authorities;
        this.name = name;
        this.fullName = fullName;
        this.userpic = userpic;
        this.email = email;
        this.locale = locale;
        this.genderEnum = genderEnum;
        this.provider = provider;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.password = password;
        this.username = username;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.enabled = enabled;
    }

    public QRPrincipalUser(UserEntity user) {
        this.uuid = user.getUuid();
        this.name = user.getUsername();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.userpic = user.getUserpic();
        this.email = user.getEmail();
        this.locale = user.getLocale();
        this.genderEnum = user.getGender();
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
        return Map.of();
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
        return user.getRoleList().stream().filter(RoleEntity::getIsActive)
                .flatMap(role -> role.getPermissions().stream())
                .filter(PermissionEntity::getIsActive)
                .map(PermissionEntity::getPermission)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public Map<String, Object> getClaims() {
        return Map.of("active",true);
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public String getGender(){
        return null;
    }

}
