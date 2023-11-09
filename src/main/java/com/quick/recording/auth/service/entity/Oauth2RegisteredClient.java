package com.quick.recording.auth.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;


@Entity(name = "oauth2_registered_client")
@Data
public class Oauth2RegisteredClient {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_id_issued_at")
    private LocalDateTime clientIdIssuedAt;
    @Column(name = "client_secret")
    private String clientSecret;
    @Column(name = "client_secret_expires_at")
    private LocalDateTime clientSecretExpiresAt;
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "client_authentication_methods", length = 1000)
    private String clientAuthenticationMethod;
    @Column(name = "authorization_grant_types", length = 1000)
    private String authorizationGrantTypes;
    @Column(name = "redirect_uris", length = 1000)
    private String redirectUris;
    @Column(name = "scopes", length = 1000)
    private String scopes;
    @Column(name = "client_settings", length = 2000)
    private String clientSettings;
    @Column(name = "token_settings ", length = 2000)
    private String tokenSettings;

}
