insert into area(name, description) values ('IT', 'Informatica');
insert into area(name, description) values ('IT', 'Network');
insert into area(name, description) values ('IT', 'Java');

insert into people(name, email, username, password) values ('Ricardo', 'aa@gmail.com', 'ric', '123');
insert into people(name, email, username, password) values ('Joao', 'jj@gmail.com', 'js', '456');
insert into people(name, email, username, password) values ('Rafael', 'rr@gmail.com', 'rf', '890');
insert into people(name, email, username, password) values ('José', 'js@gmail.com', 'jose', '321');

insert into project(name, start_Date, end_Date, area_Id) values ('POC', '19/02/2024', '28/02/2024', 1);
insert into project(name, start_Date, end_Date, area_Id) values ('Spring', '19/02/2024', '28/02/2024', 2);
insert into project(name, start_Date, end_Date, area_Id) values ('Mock', '19/02/2024', '28/02/2024', 3);

INSERT INTO ticket (title, description, status, priority, progress, estimate, type, project_id, assigned_to_id, created_by_id, created_At) VALUES ('Ticket 1', 'Descrição do Ticket 1', 'NEW', 'LOW', 0, 10, 'OTHER', 1, 1, 1, CURRENT_TIMESTAMP);
INSERT INTO ticket (title, description, status, priority, progress, estimate, type, project_id, assigned_to_id, created_by_id, created_At) VALUES ('Ticket 2', 'Descrição do Ticket 2', 'NEW', 'LOW', 0, 10, 'OTHER', 2, 2, 2, CURRENT_TIMESTAMP);
INSERT INTO ticket (title, description, status, priority, progress, estimate, type, project_id, assigned_to_id, created_by_id, created_At) VALUES ('Ticket 3', 'Descrição do Ticket 3', 'NEW', 'LOW', 0, 10, 'OTHER', 3, 3, 3, CURRENT_TIMESTAMP);