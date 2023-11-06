package com.quick.recording.auth.service.security.entity;

import com.quick.recording.auth.service.security.enumeration.AuthProvider;
import com.quick.recording.auth.service.security.enumeration.Gender;
import com.quick.recording.auth.service.security.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "qr_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "userpic")
    private String userpic;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "locale")
    private String locale;

    @Column(name = "username")
    private String username;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "verified")
    private Boolean verified = false;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "provider",nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "password")
    private String password;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User2Role",
            joinColumns = { @JoinColumn(name = "user2role_id") },
            inverseJoinColumns = { @JoinColumn(name = "role2user_id") }
    )
    private List<RoleEntity> roleList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity role : getRoleList()) {
            for (PermissionEntity permission : role.getPermissionList()) {
                authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
