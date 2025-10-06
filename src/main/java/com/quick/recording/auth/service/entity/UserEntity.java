package com.quick.recording.auth.service.entity;

import com.quick.recording.auth.service.listener.UserListener;
import com.quick.recording.gateway.entity.SmartEntity;
import com.quick.recording.resource.service.enumeration.AuthProvider;
import com.quick.recording.resource.service.enumeration.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "qr_user")
@Data
@EntityListeners(UserListener.class)
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends SmartEntity {

    public UserEntity() {
    }

    @Builder
    public UserEntity(UUID uuid, String createdBy, LocalDateTime createdWhen, String updatedBy, LocalDateTime updatedWhen, Boolean isActive, String fullName, String firstName, String lastName, String userpic, String email, Boolean emailVerified, String locale, String username, LocalDateTime lastVisit, Gender gender, String phoneNumber, Boolean phoneNumberVerified, LocalDate birthDay, AuthProvider provider, String providerId, String password, Boolean accountNonExpired, Boolean credentialsNonExpired, Boolean accountNonLocked, Boolean enabled, List<RoleEntity> roleList) {
        super(uuid, createdBy, createdWhen, updatedBy, updatedWhen, isActive);
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userpic = userpic;
        this.email = email;
        this.emailVerified = emailVerified;
        this.locale = locale;
        this.username = username;
        this.lastVisit = lastVisit;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.phoneNumberVerified = phoneNumberVerified;
        this.birthDay = birthDay;
        this.provider = provider;
        this.providerId = providerId;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.roleList = roleList;
    }

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "userpic")
    private String userpic;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

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

    @Column(name = "phone_number_verified")
    private Boolean phoneNumberVerified = false;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "provider", nullable = false)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User2Role",
            joinColumns = {@JoinColumn(name = "user2role_id")},
            inverseJoinColumns = {@JoinColumn(name = "role2user_id")}
    )
    private List<RoleEntity> roleList;



}
