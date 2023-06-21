use reservation;
create table if not exists
    `FACILITY`
(
    ID            int          not null primary key auto_increment,
    FACILITY_NAME varchar(130) not null,
    CAPACITY      int          not null,
    LOCATION      varchar(130) not null,
    START_TIME    bigint       not null,
    END_TIME      bigint       not null,
    DURATION_MIN  bigint       not null ,
    DURATION_MAX  bigint       not null ,
    constraint RANGE_CHECK
        check (FACILITY.CAPACITY >= 0 and DURATION_MIN > 0 and DURATION_MAX > 0),
    constraint START_TIME_RANGE
        check ( START_TIME >= 0 and START_TIME <= 86399999 ),
    constraint END_TIME_RANGE
        CHECK ( END_TIME >= 0 and END_TIME <= 86400000 AND END_TIME > FACILITY.START_TIME ),
    constraint DURATION_CHECK
        check ( DURATION_MAX > FACILITY.DURATION_MIN and DURATION_MAX <= FACILITY.END_TIME - FACILITY.START_TIME)
) charset = utf8mb4;