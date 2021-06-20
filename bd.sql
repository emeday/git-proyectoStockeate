create database stockeate;
use stockeate

create table productos(
    id_prod int not null auto_increment primary key,
    descripcion varchar(50) not null,
    unidad_med varchar (50) not null,
    stock decimal(7,0) not null,
    precio decimal(7,2) not null
);

create table detalle_venta(
    id_venta int not null auto_increment primary key,
    id_prod int not null,
    cantidad decimal(7,2),
    subtotal decimal(7,2),
    total_IGV decimal (7,2),
    FOREIGN KEY (id_prod) references productos (id_prod)
);

insert into productos values(null,'papa','kg',20,'2.0');
insert into productos values(null,'camote','kg',15,'2.0');
insert into productos values(null,'cafe','uni',10,'1.0');
insert into productos values(null,'pollo','kg',5,'7.5');
insert into productos values(null,'agua','uni',10,'2.5');
insert into productos values(null,'comida cat','kg',25,'2.3');
insert into productos values(null,'cerveza','uni',100,'6.0');


insert into detalle_venta values(null,7,50,50*6,(50*6)+((50*6)*0.18)); -- Todo valor numerico es obtenido desde un text
update productos set stock=50 where id_prod=7; -- El valor del campo stock lo obtendremos de un text