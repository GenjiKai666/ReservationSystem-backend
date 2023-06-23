use reservation;
create table if not exists
    `RESERVATION`
(
    ID            int          not null primary key auto_increment,
    USER_ID int not null,
    FACILITY_ID int not null ,
    START_TIME bigint not null ,
    END_TIME bigint not null ,
    STATUS int not null
) charset = utf8mb4;