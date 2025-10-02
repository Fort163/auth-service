insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'ADMIN');
insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'SIMPLE_USER');
insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'OWNER');
insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'MANAGER');
insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'WORKER');
insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'ASSISTANT');

insert into role2permission
values ((select uuid from role_entity where name = 'ADMIN'),
        (select uuid from permission_entity where permission = 'PERMISSION_ADMIN'));
insert into role2permission
values ((select uuid from role_entity where name = 'ADMIN'),
        (select uuid from permission_entity where permission = 'ALL_READ'));
insert into role2permission
values ((select uuid from role_entity where name = 'ADMIN'),
        (select uuid from permission_entity where permission = 'ALL_CREATE'));
insert into role2permission
values ((select uuid from role_entity where name = 'ADMIN'),
        (select uuid from permission_entity where permission = 'ALL_EDIT'));
insert into role2permission
values ((select uuid from role_entity where name = 'ADMIN'),
        (select uuid from permission_entity where permission = 'ALL_DELETE'));