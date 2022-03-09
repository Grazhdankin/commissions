create table turnovers
(
    id              bigserial not null,
    client_id       bigint    not null,
    month           date      not null,
    turnover_amount numeric   not null,

    constraint pk_turnovers primary key (id)
);

create unique index idx_turnovers_client_id_month on turnovers (client_id, month);
alter table turnovers
    add constraint uq_turnovers_client_id_month unique using index idx_turnovers_client_id_month;

create table client_discount_rules
(
    id         bigserial not null,
    client_id  bigint    not null,
    commission numeric   not null,

    constraint pk_client_discount_rules primary key (id)
);

create unique index idx_client_discount_rules_client_id on client_discount_rules (client_id);
alter table client_discount_rules
    add constraint uq_client_discount_rules_client_id unique using index idx_client_discount_rules_client_id;

create table turnover_discount_rules
(
    id         bigserial not null,
    turnover   numeric   not null,
    commission numeric   not null,

    constraint pk_turnover_discount_rules primary key (id)
);

create unique index idx_turnover_discount_rules_turnover on turnover_discount_rules (turnover);
alter table turnover_discount_rules
    add constraint uq_turnover_discount_rules_turnover unique using index idx_turnover_discount_rules_turnover;

create table default_rules
(
    id                 bigserial not null,
    commission_rate    numeric   not null,
    commission_minimum numeric   not null,
    actual             boolean   not null default false,

    constraint pk_default_rules primary key (id)
);

create unique index idx_default_rules_actual on default_rules (actual) where actual = true;
create unique index idx_default_rules_commission_rate_commission_minimum on default_rules (commission_rate, commission_minimum);
alter table default_rules
    add constraint uq_default_rules_commission_rate_commission_minimum unique using index idx_default_rules_commission_rate_commission_minimum;