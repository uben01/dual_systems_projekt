create table if not exists invoice
(
	id varchar not null
		constraint invoice_pk
			primary key,
	customername varchar not null,
	issuedate date not null,
	duedate date not null,
	comment text
);

alter table invoice owner to postgres;

create table if not exists item
(
	id varchar not null
		constraint item_pk
			primary key,
	unitprice integer not null,
	quantity integer not null,
	invoice_id varchar
		constraint item_invoice_id_fk
			references invoice,
	productname varchar not null
);

alter table item owner to postgres;

insert into invoice (id, customername, issuedate, duedate, comment) VALUES ('1', 'Customer0', '2020-01-01', '2020-02-02', 'This is a comment');
insert into invoice (id, customername, issuedate, duedate) VALUES ('2', 'Customer2', '2020-01-03', '2020-01-08');
insert into invoice (id, customername, issuedate, duedate) VALUES ('3', 'Customer3', '2020-03-04', '2020-03-05');
insert into invoice (id, customername, issuedate, duedate, comment) VALUES ('4', 'Customer4', '2020-08-01', '2020-09-02', 'This is a comment as well');


insert into item(id, productname, unitprice, quantity, invoice_id) VALUES ('1', 'product1', 1000, 2, 1);
insert into item(id, productname, unitprice, quantity, invoice_id) VALUES ('2', 'product7', 20000, 4, 1);
insert into item(id, productname, unitprice, quantity, invoice_id) VALUES ('3', 'product1', 1000, 7, 1);

insert into item(id, productname, unitprice, quantity, invoice_id) VALUES ('4', 'product5', 8000, 32, 2);
insert into item(id, productname, unitprice, quantity, invoice_id) VALUES ('5', 'product7', 20000, 32, 2);
insert into item(id, productname, unitprice, quantity, invoice_id) VALUES ('6', 'product2', 1000, 32, 2);

insert into item(id, productname, unitprice, quantity, invoice_id) VALUES ('7', 'product2', 4000000, 32, 3);