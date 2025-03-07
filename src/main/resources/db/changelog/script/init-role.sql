insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'SIMPLE_USER');
insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'SPACE_ADMIN');
insert into role_entity
values (gen_random_uuid(), 'auth-service', current_timestamp, 'auth-service', current_timestamp, true, 'SERVICE');

insert into role2permission
values ((select uuid from role_entity where name = 'SIMPLE_USER'),
        (select uuid from permission_entity where permission = 'ROLE_READ'));
insert into role2permission
values ((select uuid from role_entity where name = 'SIMPLE_USER'),
        (select uuid from permission_entity where permission = 'ROLE_CHANGE_ME_INFO'));

insert into role2permission
values ((select uuid from role_entity where name = 'SPACE_ADMIN'),
        (select uuid from permission_entity where permission = 'ROLE_CREATE_SPACE'));
insert into role2permission
values ((select uuid from role_entity where name = 'SPACE_ADMIN'),
        (select uuid from permission_entity where permission = 'ROLE_WRITE'));
insert into role2permission
values ((select uuid from role_entity where name = 'SPACE_ADMIN'),
        (select uuid from permission_entity where permission = 'ROLE_READ'));

insert into role2permission
values ((select uuid from role_entity where name = 'SERVICE'),
        (select uuid from permission_entity where permission = 'ROLE_READ'));
insert into role2permission
values ((select uuid from role_entity where name = 'SERVICE'),
        (select uuid from permission_entity where permission = 'ROLE_WRITE'));