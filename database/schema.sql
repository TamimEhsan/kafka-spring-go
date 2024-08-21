create schema if not exists tenant1;
create schema if not exists tenant2;
create schema if not exists tenant3;



create table tenant1.person (id serial not null, name varchar(255), primary key (id));
create table tenant2.person (id serial not null, name varchar(255), primary key (id));
create table tenant3.person (id serial not null, name varchar(255), primary key (id));
create table person (id serial not null, name varchar(255), primary key (id));

-- create sequences
create sequence tenant1.person_seq start with 1 increment by 50;
create sequence tenant2.person_seq start with 1 increment by 50;
create sequence tenant3.person_seq start with 1 increment by 50;
create sequence person_seq start with 1 increment by 50;


-- INSERT INTO tenant1.person (name) VALUES ('user1');
-- INSERT INTO tenant2.person (name) VALUES ('user2');
-- INSERT INTO tenant3.person (name) VALUES ('user3');

-- INSERT INTO person (name) VALUES ('public user');


