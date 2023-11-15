insert into qr_user(uuid, birth_day, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled)
values (gen_random_uuid(), '2000-01-01', 'company-service', 'company-service', 'NOT_DEFINED', 'service',
        true, true, true, true);
insert into qr_user(uuid, birth_day, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled)
values (gen_random_uuid(), '2000-01-01', 'user-service', 'user-service', 'NOT_DEFINED', 'service',
        true, true, true, true);
insert into qr_user(uuid, birth_day, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled)
values (gen_random_uuid(), '2000-01-01', 'part-time-service', 'part-time-service', 'NOT_DEFINED','service',
        true, true, true, true);
insert into qr_user(uuid, birth_day, full_name, username, gender, provider, account_non_expired,
                    account_non_locked, credentials_non_expired, enabled)
values (gen_random_uuid(), '2000-01-01', 'qr-b2b-service', 'qr-b2b-service', 'NOT_DEFINED', 'service',
        true, true, true, true);

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where username = 'company-service'),
        (select uuid from role_entity where name = 'SERVICE'));

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where username = 'user-service'),
        (select uuid from role_entity where name = 'SERVICE'));

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where username = 'part-time-service'),
        (select uuid from role_entity where name = 'SERVICE'));

insert into user2role(user2role_id, role2user_id)
values ((select uuid from qr_user where username = 'qr-b2b-service'),
        (select uuid from role_entity where name = 'SPACE_ADMIN'));
