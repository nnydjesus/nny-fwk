alter table Product drop foreign key FK50C664CFCC480F6D
alter table ProductSold drop foreign key FKA80CE43CC480F6D
alter table Sale_ProductSold drop foreign key FK771DFE6BFEDEF118
alter table Sale_ProductSold drop foreign key FK771DFE6B143E2587
drop table if exists Product
drop table if exists ProductSold
drop table if exists ProductType
drop table if exists Sale
drop table if exists Sale_ProductSold
create table Product (id varchar(36) not null, stateVersion bigint, valueKey varchar(255), buyPrice double precision not null, code varchar(255) unique, maturity datetime, minStock integer not null, name varchar(255) unique, percentage double precision not null, stock integer not null, productType_id varchar(36), primary key (id))
create table ProductSold (id varchar(36) not null, stateVersion bigint, valueKey varchar(255), buyPrice double precision not null, cant integer not null, code varchar(255), ganancia double precision not null, name varchar(255), sellPrice double precision not null, totalSold double precision not null, productType_id varchar(36), primary key (id))
create table ProductType (id varchar(36) not null, stateVersion bigint, valueKey varchar(255), name varchar(255), primary key (id))
create table Sale (id varchar(36) not null, stateVersion bigint, valueKey varchar(255), date date, total double precision not null, primary key (id))
create table Sale_ProductSold (Sale_id varchar(36) not null, soldProducts_id varchar(36) not null, unique (soldProducts_id))
create index INDX_key on Product (valueKey)
alter table Product add index FK50C664CFCC480F6D (productType_id), add constraint FK50C664CFCC480F6D foreign key (productType_id) references ProductType (id)
create index INDX_key on ProductSold (valueKey)
alter table ProductSold add index FKA80CE43CC480F6D (productType_id), add constraint FKA80CE43CC480F6D foreign key (productType_id) references ProductType (id)
create index INDX_key on ProductType (valueKey)
create index INDX_key on Sale (valueKey)
alter table Sale_ProductSold add index FK771DFE6BFEDEF118 (soldProducts_id), add constraint FK771DFE6BFEDEF118 foreign key (soldProducts_id) references ProductSold (id)
alter table Sale_ProductSold add index FK771DFE6B143E2587 (Sale_id), add constraint FK771DFE6B143E2587 foreign key (Sale_id) references Sale (id)
