use reservation;
create table if not exists
    `USER`
(
    ID  int not null primary key auto_increment,
    USERNAME varchar(130) not null unique,
    PASSWORD varchar(100) not null,
    STATUS int not null,
    CREDIT int not null,
    constraint USERNAME_UNIQUE
        unique (USERNAME),
    constraint CREDIT_RANGE
        check (CREDIT>=0 and CREDIT<=100)
) charset = utf8mb4;

create table if not exists
    `ADMIN`
(
    ID  int not null primary key auto_increment,
    USERNAME varchar(130) not null unique,
    PASSWORD varchar(100) not null,
    constraint USERNAME_UNIQUE
        unique (USERNAME)
) charset = utf8mb4;