-- Database Mega Moto Market;
create database motoequipment;
use motoequipment;
-- tables equipment, manufacturer, supplier, warehouse

-- drop table if exists 'manufacturer';
create table manufacturer(id int primary key AUTO_INCREMENT,
                          name varchar(50) not null unique,
                          address varchar(50) not null,
                          phone varchar(15));

insert into manufacturer (name, address, phone) values ('Dragon Fly', 'str. Igor Vieru 22', '+37369982418'),
                                                       ('Moto Lider', 'str. Stefan cel Mare 11', '+37378786543'),
                                                       ('Drive Bike', 'str. Arborilor 44', '+37367766776'),
                                                       ('MotoXmotO', 'str. Puskin 12', '+37378444444'),
                                                       ('Mr. Moto', 'str. Kiev 5', '+37360060600');


-- drop table if exists 'supplier';
create table supplier (id int primary key AUTO_INCREMENT,
                       name varchar(50) unique,
                       manufacturer_id int,
                       foreign key (manufacturer_id) references manufacturer(id));

insert into supplier (name, manufacturer_id) values ('Dragon Fly supply', 1),
                                                    ('Moto Lider supply', 2),
                                                    ('Drive Bike supply', 3),
                                                    ('MotoXmotO supply', 4),
                                                    ('Mr. Moto supply', 5);

-- drop table if exists 'equipment';
create table equipment (id int primary key AUTO_INCREMENT,
                        name varchar(50) unique,
                        type enum('HELMET', 'JACKET', 'PANTS', 'GLOVES', 'SHOES', 'BODY_ARMOR', 'GLASSES', 'ACCESSORY') not null,
                        price int,
                        size varchar(4) default 'M',
                        weight int,
                        manufacturer_id int,
                        foreign key (manufacturer_id) references manufacturer(id),
                        supplier_id int,
                        foreign key (supplier_id) references supplier(id));

insert into equipment (name, type, price, size, weight, manufacturer_id, supplier_id) values ('Fire Helmet', 'HELMET', 2000, 'X', 1000, 1, 1),
                                                                                     ('Fire Bird', 'JACKET', 5000, 'XL', 1500, 2, 2),
                                                                                     ('Classic Rock', 'PANTS', 3500, 'XS', 900, 3, 3),
                                                                                     ('Only Up', 'GLOVES', 2000, 'M', 200, 4, 4),
                                                                                     ('BMW v2', 'SHOES', 3700, 'S', 1000, 5, 5),
                                                                                     ('Protector 360', 'BODY_ARMOR', 2900, 'L', 3000, 2, 2),
                                                                                     ('Aviators', 'GLASSES', 1000, 'XXL', 100, 1, 1),
                                                                                     ('Bike in fire sticker', 'ACCESSORY', 500, 'S', 100, 3, 3);

-- drop table if exists 'warehouse';
create table warehouse (id int primary key AUTO_INCREMENT,
                        name varchar(50) unique,
                        address varchar(70));

insert into warehouse (name, address) values ('Mega Moto Market Warehouse 1', 'str. Stefan cel MAre 2'),
                                             ('Mega Moto Market Warehouse 2', 'str. Mircea cel Batrin 17/5');

-- drop table if exists 'warehouse_equipment';
create table warehouse_equipment(equipment_id int,
                                 warehouse_id int,
                                 foreign key (equipment_id) references equipment(id) on delete cascade,
                                 foreign key (warehouse_id) references warehouse(id) on delete cascade);

insert into warehouse_equipment (equipment_id, warehouse_id) values (8, 1),
                                                                    (5, 1),
                                                                    (1, 2),
                                                                    (2, 1),
                                                                    (7, 1),
                                                                    (3, 2),
                                                                    (4, 2),
                                                                    (6, 2);

