DROP TABLE IF EXISTS WEATHER;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 0 increment by 1;

create table WEATHER (
  id INT AUTO_INCREMENT PRIMARY KEY,
  date DATE NOT NULL,
  city VARCHAR(250),
  state VARCHAR(250),
  temperature FLOAT
);

