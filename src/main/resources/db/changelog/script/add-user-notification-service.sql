insert into qr_user(uuid, birth_day, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled)
values (gen_random_uuid(), '2000-01-01', 'notification-service', 'notification-service', 'NOT_DEFINED', 'service',
        true, true, true, true);

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where username = 'notification-service'),
        (select uuid from role_entity where name = 'SERVICE'));