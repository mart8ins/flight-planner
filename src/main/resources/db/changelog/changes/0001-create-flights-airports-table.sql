
--liquibase formatted sql
--changeset martins:1

create table airports(
        airport_id varchar(10) primary key,
        country varchar(255) not null,
        city varchar(255) not null
);

create table flights(
        flight_id serial primary key,
        carrier varchar(255) not null,
        departure_time timestamp not null,
        arrival_time timestamp not null,
        airport_from_id varchar(10) not null,
        airport_to_id varchar(10) not null,
        foreign key (airport_from_id) references airports (airport_id) ON DELETE CASCADE ON UPDATE CASCADE,
        foreign key (airport_to_id) references airports (airport_id) ON DELETE CASCADE ON UPDATE CASCADE
);