                                                                                                                                                         insert into plant_inventory_entry (id, name, description, price) values (1, 'Mini excavator', '1.5 Tonne Mini excavator', 150);
insert into plant_inventory_entry (id, name, description, price) values (2, 'Mini excavator1', '3 Tonne Mini excavator', 200);
insert into plant_inventory_entry (id, name, description, price) values (3, 'Midi excavator2', '5 Tonne Midi excavator', 250);
insert into plant_inventory_entry (id, name, description, price) values (4, 'Midi excavator3', '8 Tonne Midi excavator', 300);
insert into plant_inventory_entry (id, name, description, price) values (5, 'Maxi excavator4', '15 Tonne Large excavator', 400);
insert into plant_inventory_entry (id, name, description, price) values (6, 'Maxi excavator', '20 Tonne Large excavator', 450);
insert into plant_inventory_entry (id, name, description, price) values (7, 'HS dumper', '1.5 Tonne Hi-Swivel Dumper', 150);
insert into plant_inventory_entry (id, name, description, price) values (8, 'FT dumper21', '2 Tonne Front Tip Dumper', 180);
insert into plant_inventory_entry (id, name, description, price) values (9, 'FT dumper1111', '2 Tonne Front Tip Dumper', 200);
insert into plant_inventory_entry (id, name, description, price) values (10, 'FT dumper21122', '2 Tonne Front Tip Dumper', 300);
insert into plant_inventory_entry (id, name, description, price) values (11, 'FT dumper', '3 Tonne Front Tip Dumper', 400);
insert into plant_inventory_entry (id, name, description, price) values (12, 'Loader', 'Hewden Backhoe Loader', 200);
insert into plant_inventory_entry (id, name, description, price) values (13, 'D-Truck', '15 Tonne Articulating Dump Truck', 250);
insert into plant_inventory_entry (id, name, description, price) values (14, 'D-Truck212112', '30 Tonne Articulating Dump Truck', 300);

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
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (11, 11, 'A09', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (12, 12, 'A09', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (13, 13, 'A09', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (14, 14, 'A09', 'SERVICEABLE');

insert into plant_reservation (id, plant_id, start_date, end_date) values (1, 1, '2017-02-10', '2017-03-24');
insert into plant_reservation (id, plant_id, start_date, end_date) values (2, 3, '2017-12-15','2017-12-20');

insert into purchase_order (id, issue_date, payment_schedule, total, status, start_date, end_date, plant_id, reservation_id) values (1, '2017-02-10', '2017-03-24', 300, 'CANCEL','2017-02-10','2017-03-24',1,1);
insert into purchase_order (id, total, status, start_date, end_date, plant_id, reservation_id) values (2, 300, 'OPEN','2017-06-20','2017-06-25',9,2);
insert into purchase_order (id, total, status, start_date, end_date, plant_id) values (3, 1200, 'PENDING','2017-12-22','2017-12-25',9);
insert into purchase_order (id, total, status, start_date, end_date, plant_id) values (5, 1200, 'PENDING','2017-12-22','2017-12-25',9);
insert into purchase_order (id, total, status, start_date, end_date, plant_id) values (4, 1200, 'CANCEL_PENDING','2017-12-15','2017-12-20',3);







