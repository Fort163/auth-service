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

alter table oauth2_registered_client
    owner to postgres;

create table permission_entity
(
    uuid       uuid not null
        constraint permission_entity_pkey
            primary key,
    permission varchar(255)
        constraint uk_permission_permission
            unique
);

alter table permission_entity
    owner to postgres;

create table role_entity
(
    uuid uuid not null
        constraint role_entity_pkey
            primary key,
    name varchar(255)
        constraint uk_role_name
            unique
);

alter table role_entity
    owner to postgres;

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
    enabled                 boolean,
    first_name              varchar(255),
    full_name               varchar(255),
    gender                  varchar(255),
    last_name               varchar(255),
    last_visit              timestamp(6),
    locale                  varchar(255),
    password                varchar(255),
    phone_number            varchar(255),
    provider                varchar(255) not null,
    provider_id             varchar(255)
        constraint uk_user_provider_id
            unique,
    status                  varchar(255),
    username                varchar(255),
    userpic                 varchar(255),
    verified                boolean
);

alter table qr_user
    owner to postgres;

create table role2permission
(
    role2permission_id uuid not null
        constraint fkf161uowi91qfwpo9fq3gccy3q
            references role_entity,
    permission2role_id uuid not null
        constraint fk4pxe3ac9lde4132ym5snnahix
            references permission_entity
);

alter table role2permission
    owner to postgres;

create table user2role
(
    user2role_id uuid not null
        constraint fk9cw2osmsm3mwfhpef05f1itx7
            references qr_user,
    role2user_id uuid not null
        constraint fk9n0u2o2x6janlj6spfqxoprb4
            references role_entity
);

alter table user2role
    owner to postgres;







