create table oauth2_registered_client
(
    id                            varchar(255) not null
        constraint oauth2_registered_client_pkey
            primary key,
    authorization_grant_types     varchar(1000),
    client_authentication_methods varchar(1000),
    client_id                     varchar(255),
    client_id_issued_at           timestamp(6),
    client_name                   varchar(255),
    client_secret                 varchar(255),
    client_secret_expires_at      timestamp(6),
    client_settings               varchar(2000),
    redirect_uris                 varchar(1000),
    scopes                        varchar(1000),
    token_settings                varchar(2000)
);

create table permission_entity
(
    uuid         uuid not null
        constraint permission_entity_pkey
            primary key,
    created_by   varchar(255),
    created_when timestamp,
    updated_by   varchar(255),
    updated_when timestamp,
    is_active    boolean default true,
    permission   varchar(255)
        constraint uk_permission_permission
            unique
);

create table role_entity
(
    uuid         uuid not null
        constraint role_entity_pkey
            primary key,
    created_by   varchar(255),
    created_when timestamp,
    updated_by   varchar(255),
    updated_when timestamp,
    is_active    boolean default true,
    name         varchar(255)
        constraint uk_role_name
            unique
);

create table qr_user
(
    uuid                    uuid         not null
        constraint qr_user_pkey
            primary key,
    account_non_expired     boolean,
    account_non_locked      boolean,
    birth_day               date,
    credentials_non_expired boolean,
    email                   varchar(255)
        constraint uk_user_email
            unique,
    email_verified          boolean,
    enabled                 boolean,
    first_name              varchar(255),
    full_name               varchar(255),
    gender                  varchar(255),
    last_name               varchar(255),
    last_visit              timestamp(6),
    locale                  varchar(255),
    password                varchar(255),
    phone_number            varchar(255),
    phone_number_verified   boolean,
    provider                varchar(255) not null,
    provider_id             varchar(255)
        constraint uk_user_provider_id
            unique,
    username                varchar(255)
        constraint uk_user_username
            unique,
    userpic                 text
);

create table role2permission
(
    role2permission_id uuid not null
        constraint fkf161uowi91qfwpo9fq3gccy3q
            references role_entity,
    permission2role_id uuid not null
        constraint fk4pxe3ac9lde4132ym5snnahix
            references permission_entity
);

create table user2role
(
    user2role_id uuid not null
        constraint fk9cw2osmsm3mwfhpef05f1itx7
            references qr_user,
    role2user_id uuid not null
        constraint fk9n0u2o2x6janlj6spfqxoprb4
            references role_entity
);

create table oauth2_authorization
(
    uuid                          uuid         NOT NULL
        constraint oauth2_authorization_pkey
            primary key,
    registered_client_id          varchar(100),
    qr_user_id                    uuid
        constraint qr_user_fk
            references qr_user,
    principal_name                varchar(200) NOT NULL,
    attributes                    jsonb         DEFAULT NULL,
    authorization_grant_type      varchar(100) NOT NULL,
    state                         varchar(500)  DEFAULT NULL,
    authorization_code_value      varchar(1000) DEFAULT NULL,
    authorization_code_issued_at  timestamp     DEFAULT NULL,
    authorization_code_expires_at timestamp     DEFAULT NULL,
    access_token_value            varchar(1000) DEFAULT NULL,
    access_token_issued_at        timestamp     DEFAULT NULL,
    access_token_expires_at       timestamp     DEFAULT NULL,
    access_token_type             varchar(100)  DEFAULT NULL,
    oidc_id_token_value           varchar(1000) DEFAULT NULL,
    oidc_id_token_issued_at       timestamp     DEFAULT NULL,
    oidc_id_token_expires_at      timestamp     DEFAULT NULL,
    refresh_token_value           varchar(1000) DEFAULT NULL,
    refresh_token_issued_at       timestamp     DEFAULT NULL,
    refresh_token_expires_at      timestamp     DEFAULT NULL
);

CREATE TABLE SPRING_SESSION
(
    PRIMARY_ID            CHAR(36) NOT NULL,
    SESSION_ID            CHAR(36) NOT NULL,
    CREATION_TIME         BIGINT   NOT NULL,
    LAST_ACCESS_TIME      BIGINT   NOT NULL,
    MAX_INACTIVE_INTERVAL INT      NOT NULL,
    EXPIRY_TIME           BIGINT   NOT NULL,
    PRINCIPAL_NAME        VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID CHAR(36)     NOT NULL,
    ATTRIBUTE_NAME     VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES    BYTEA        NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
);





