                                                                                                                                                         insert into plant_inventory_entry (id, name, description, price) values (1, 'Mini excavator', '1.5 Tonne Mini excavator', 150);
insert into plant_inventory_entry (id, name, description, price) values (2, 'Mini excavator', '3 Tonne Mini excavator', 200);
insert into plant_inventory_entry (id, name, description, price) values (3, 'Midi excavator', '5 Tonne Midi excavator', 250);
insert into plant_inventory_entry (id, name, description, price) values (4, 'Midi excavator', '8 Tonne Midi excavator', 300);
insert into plant_inventory_entry (id, name, description, price) values (5, 'Maxi excavator', '15 Tonne Large excavator', 400);
insert into plant_inventory_entry (id, name, description, price) values (6, 'Maxi excavator', '20 Tonne Large excavator', 450);
insert into plant_inventory_entry (id, name, description, price) values (7, 'HS dumper', '1.5 Tonne Hi-Swivel Dumper', 150);
insert into plant_inventory_entry (id, name, description, price) values (8, 'FT dumper', '2 Tonne Front Tip Dumper', 180);
insert into plant_inventory_entry (id, name, description, price) values (9, 'FT dumper', '2 Tonne Front Tip Dumper', 200);
insert into plant_inventory_entry (id, name, description, price) values (10, 'FT dumper', '2 Tonne Front Tip Dumper', 300);
insert into plant_inventory_entry (id, name, description, price) values (11, 'FT dumper', '3 Tonne Front Tip Dumper', 400);
insert into plant_inventory_entry (id, name, description, price) values (12, 'Loader', 'Hewden Backhoe Loader', 200);
insert into plant_inventory_entry (id, name, description, price) values (13, 'D-Truck', '15 Tonne Articulating Dump Truck', 250);
insert into plant_inventory_entry (id, name, description, price) values (14, 'D-Truck', '30 Tonne Articulating Dump Truck', 300);



insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (1, 1, 'A01', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (2, 2, 'A02', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (3, 3, 'A03', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (4, 4, 'A04', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (5, 5, 'A05', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (6, 6, 'A06', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (7, 6, 'A07', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (8, 6, 'A08', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (9, 9, 'A09', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (10, 10, 'A09', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (11, 10, 'A09', 'SERVICEABLE');

insert into plant_reservation (id, plant_id, start_date, end_date) values (1, 1, '2017-02-10', '2017-03-24');
insert into plant_reservation (id, plant_id, start_date, end_date) values (2, 9, '2017-06-20', '2017-06-25');

insert into purchase_order (id, issue_date, payment_schedule, total, status, start_date, end_date, plant_id, reservation_id) values (1, '2017-02-10', '2017-03-24', 300, 'CANCEL','2017-02-10','2017-03-24',1,1);
insert into purchase_order (id, total, status, start_date, end_date, plant_id, reservation_id) values (2, 300, 'OPEN','2017-06-20','2017-06-25',9,2);
insert into purchase_order (id, total, status, start_date, end_date, plant_id) values (3, 1200, 'PENDING_WORKER_CONFIRM','2017-12-20','2017-12-25',9);


insert into purchase_order (id, total, status, start_date, end_date, plant_id) values (3, 1200, 'PENDING','2017-12-20','2017-12-25',9);
insert into purchase_order (id, total, status, start_date, end_date, plant_id) values (3, 1200, 'PENDING','2017-12-21','2017-12-24',9);



