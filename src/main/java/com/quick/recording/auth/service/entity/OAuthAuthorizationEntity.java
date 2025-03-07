package com.quick.recording.auth.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity(name = "oauth2_authorization")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthAuthorizationEntity extends BaseEntity {

    @Column(name = "registered_client_id")
    private String registeredClient;
    @ManyToOne
    @JoinColumn(name = "qr_user_id")
    private UserEntity user;
    @Column(name = "principal_name")
    private String principalName;
    @Column(name = "authorization_grant_type")
    private String grantType;
    @Column(name = "state")
    private String state;
    @Column(name = "authorization_code_value")
    private String authorizationCodeValue;
    @Column(name = "authorization_code_issued_at")
    private LocalDateTime authorizationCodeIssuedAt;
    @Column(name = "authorization_code_expires_at")
    private LocalDateTime authorizationCodeExpiresAt;
    @Column(name = "access_token_value")
    private String accessTokenValue;
    @Column(name = "access_token_type")
    private String accessTokenType;
    @Column(name = "access_token_issued_at")
    private LocalDateTime accessTokenIssuedAt;
    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;
    @Column(name = "oidc_id_token_value")
    private String oidcIdTokenValue;
    @Column(name = "oidc_id_token_issued_at")
    private LocalDateTime oidcIdTokenIssuedAt;
    @Column(name = "oidc_id_token_expires_at")
    private LocalDateTime oidcIdTokenExpiresAt;
    @Column(name = "refresh_token_value")
    private String refreshTokenValue;
    @Column(name = "refresh_token_issued_at")
    private LocalDateTime refreshTokenIssuedAt;
    @Column(name = "refresh_token_expires_at")
    private LocalDateTime refreshTokenExpiresAt;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attributes")
    private String attributes;

}
