--Insert customers information 1-30
insert into customers_t (code, country, email, name, surname, id)
values ('32965391046', 'LV', 'adamovics.oskars@inbox.lv', 'OSKARS', 'ADAMOVIČS', 1);
insert into customers_t (code, country, email, name, surname, id)
values ('271415050', 'LT', 'katulis.arturas@gmail.com', 'ARTŪRAS', 'KATULIS', 2);
insert into customers_t (code, country, email, name, surname, id)
values ('14028953', 'EE', 'miha@epostas.ee', 'MIHHAIL', 'TÕRTÕŠNÕI', 3);
insert into customers_t (code, country, email, name, surname, id)
values ('647556834', 'PL', 'constructor@house.pl', 'Przemysław', 'Wojewódka', 4);
insert into customers_t (code, country, email, name, surname, id)
values ('654734345', 'RO', 'json@path.com', 'Kazuki', 'Hamasaki', 5);

--Insert debt cases information 31-60
insert into debt_cases_t (amount, currency_code, customer_id, due_date, number, id)
values (12.21, 'EUR', 1, '2022-02-27', 'DCN-LV-1234', 31);
insert into debt_cases_t (amount, currency_code, customer_id, due_date, number, id)
values (99.00, 'USD', 1, '2022-04-01', 'DCN-LV-1235', 32);
insert into debt_cases_t (amount, currency_code, customer_id, due_date, number, id)
values (14.21, 'EUR', 2, '2022-07-01', 'DCN-LT-1', 33);
insert into debt_cases_t (amount, currency_code, customer_id, due_date, number, id)
values (17.01, 'CHF', 2, '2022-04-01', 'DCN-SR-1', 34);

--Insert users information 61-90
insert into users_t (customer_id, password_hash, username, id)
values (1, 'cGFzc3dvcmQxMjM0', 'OSKADA', 61); --Password -> password1234
insert into users_t (customer_id, password_hash, username, id)
values (2, 'cGFzc3dvcmQxMjM0', 'ARTKAT', 62); --Password -> password1234
commit;