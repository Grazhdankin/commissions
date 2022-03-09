insert into default_rules(commission_rate, commission_minimum, actual)
values (0.005, 0.05, true);

insert into client_discount_rules(client_id, commission)
values (42, 0.05);

insert into turnover_discount_rules(turnover, commission)
values (1000, 0.03);