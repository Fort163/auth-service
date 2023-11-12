insert into qr_user(uuid, birth_day, email, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled, verified)
values (gen_random_uuid(), '2000-01-01', 'company', 'company-service', 'company-service', 'NOT_DEFINED', 'service',
        true, true, true, true, true);
insert into qr_user(uuid, birth_day, email, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled, verified)
values (gen_random_uuid(), '2000-01-01', 'user', 'user-service', 'user-service', 'NOT_DEFINED', 'service', true, true,
        true, true, true);
insert into qr_user(uuid, birth_day, email, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled, verified)
values (gen_random_uuid(), '2000-01-01', 'part-time', 'part-time-service', 'part-time-service', 'NOT_DEFINED',
        'service', true, true, true, true, true);
insert into qr_user(uuid, birth_day, email, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled, verified)
values (gen_random_uuid(), '2000-01-01', 'qr-b2b', 'qr-b2b-service', 'qr-b2b-service', 'NOT_DEFINED', 'service', true,
        true, true, true, true);

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where email = 'company'),
        (select uuid from role_entity where name = 'SERVICE'));

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where email = 'user'),
        (select uuid from role_entity where name = 'SERVICE'));

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where email = 'part-time'),
        (select uuid from role_entity where name = 'SERVICE'));

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where email = 'qr-b2b'),
        (select uuid from role_entity where name = 'SPACE_ADMIN'));
