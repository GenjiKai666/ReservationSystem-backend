use reservation;
create table if not exists
    `RESERVATION`
(
    ID          int    not null primary key auto_increment,
    USER_ID     int    not null,
    FACILITY_ID int    not null,
    START_TIME  bigint not null,
    END_TIME    bigint not null,
    STATUS      int    not null,
    constraint FK_USER_ID
        foreign key (USER_ID) references USER (ID)
            on delete cascade on update cascade,
    constraint FK_FACILITY_ID
        foreign key (FACILITY_ID) references FACILITY (ID)
            on delete cascade on update cascade
) charset = utf8mb4;
create table if not exists
    `COMMENT`
(
    ID             int  not null primary key auto_increment,
    USER_ID        int  not null,
    FACILITY_ID    int  not null,
    RESERVATION_ID int  not null,
    RATE           int  not null,
    CONTENT        text not null,
    constraint FK_USER_ID_2
        foreign key (USER_ID) references USER (ID)
            on delete cascade on update cascade,
    constraint FK_FACILITY_ID_2
        foreign key (FACILITY_ID) references FACILITY (ID)
            on delete cascade on update cascade,
    constraint FK_RESERVATION_ID
        foreign key (RESERVATION_ID) references RESERVATION (ID)
            on delete cascade on update cascade
) charset = utf8mb4;