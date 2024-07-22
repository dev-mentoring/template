/* 유저 더미 데이터 */
insert into users (email, name, password, phone, is_deleted, created_at, updated_at)
values ('test@test.com', 'test', '$2a$10$v.DHYimH.eimSYeMF2vtdeOHgbu3JElaN.OPBzaYyQzlwrwbBeVD2', '010-0000-0000', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into user_roles (user_email, roles)
values ('test@test.com', 'ADMIN'),
       ('test@test.com', 'USER');
