create sequence hibernate_sequence start with 100 increment by 1;
create table customers_t
(
    id      bigint      not null,
    country varchar(2)  not null,
    email   varchar(50) not null,
    name    varchar(50) not null,
    surname varchar(50) not null,
    code    varchar(20) not null,
    primary key (id)
);

alter table customers_t
    add constraint UK_CUSTOMER_CODE unique (code);

create table debt_cases_t
(
    id            bigint         not null,
    amount        decimal(19, 2) not null,
    currency_code varchar(3)     not null,
    customer_id   bigint         not null,
    due_date      date           not null,
    number        varchar(50)    not null,
    primary key (id)
);
alter table debt_cases_t
    add constraint FK_DEBT_CUSTOMER_ID
        foreign key (customer_id)
            references customers_t (id);
alter table debt_cases_t
    add constraint UK_DEBT_CASE_NUMBER unique (number);

create table users_t
(
    id            bigint      not null,
    customer_id   bigint      not null,
    password_hash varchar(60) not null,
    username      varchar(50) not null,
    primary key (id)
);
alter table users_t
    add constraint UK_USERNAME unique (username);

alter table users_t
    add constraint UK_CUSTOMER_ID unique (customer_id);

alter table users_t
    add constraint FK_USER_CUSTOMER_ID
        foreign key (customer_id)
            references customers_t (id);
commit;