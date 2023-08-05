create sequence users_id_seq start 5;
create sequence users_credentials_id_seq start 5;

create table if not exists roles
(
    id   integer primary key,
    name varchar not null
);

create table if not exists users
(
    id        integer primary key,
    firstName varchar not null,
    lastName  varchar not null
);

create table if not exists user_credentials
(
    id       integer primary key,
    user_id  integer not null,
    username varchar not null,
    password varchar not null,

    constraint FK_user_id foreign key (user_id) references users (id)
);

create table if not exists users_roles
(
    user_id integer,
    role_id integer,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

insert into users (id, firstName, lastName)
values (1, 'Василий', 'Петров'),
       (2, 'Артем', 'Смирнов');

insert into roles (id, name)
values (1, 'USER'),
       (2, 'ADMIN');

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 1);

insert into user_credentials (id, user_id, username, password)
values (1, 1, 'vpetrov', '$2a$12$6x0J7MZmtAc3KFNyJYTg8OrWDVFTvwiAVv9VKy.8Qx9dKmV9DyIY2'),
       (2, 2, 'asmirnov', '$2a$12$ZdDucn4s7p2C7AF0OAewcuRunxVC9EP7L5FKXEdeCG83DSwBhgwoW');



