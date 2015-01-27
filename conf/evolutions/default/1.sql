# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table contact (
  contact_type_id           varchar(255),
  referral_type_id          varchar(255),
  title                     varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  organization              varchar(255),
  address                   varchar(255),
  city                      varchar(255),
  postal_code               varchar(255),
  state_id                  varchar(255),
  country                   varchar(255),
  phone                     varchar(255),
  email                     varchar(255),
  last_updated              date,
  notes                     varchar(255))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table contact;

SET FOREIGN_KEY_CHECKS=1;

