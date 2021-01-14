create table exchange_rate
(
	id integer not null,
	origin_currency DECIMAL(24, 8),
	destination_currency DECIMAL(24, 8),
	primary key(id)
);