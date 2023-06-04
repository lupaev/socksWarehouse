--liquibase formatted sql

--changeset sergej:2
insert into socks (color, cotton_part, quantity) values ('red', 20, 1111);
