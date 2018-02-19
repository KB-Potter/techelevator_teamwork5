Create database w6d1_database_pairs
--drop table customer;





create table pet(
pet_id serial not null,
name varchar(35) not null,
type varchar(35) not null,
age int,
customer_id int,

constraint pk_pet_id primary key(pet_id)
);

create table customer(
customer_id serial not null,
pet_id int,
name varchar(40),
address varchar(100),

constraint pk_customer_id primary key(customer_id),
constraint fk_pet_id foreign key(pet_id) references pet(pet_id)
);


alter table pet add constraint fk_customer_id foreign key(customer_id) references customer(customer_id);

create table invoice(
invoice_id int,
customer_id serial not null,
visit_date date,
procedure_id int,

constraint pk_invoice_id primary key(invoice_id),
constraint fk_customer_id foreign key(customer_id) references customer(customer_id)
);

create table pet_procedure(
name varchar(35),
cost money,
procedure_id serial not null,

constraint pk_procedure_id primary key(procedure_id)
);

alter table invoice add constraint fk_procedure_id foreign key(procedure_id) references pet_procedure(procedure_id);





