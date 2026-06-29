-- Seed data loaded into the (in-memory H2) database on every startup.
-- Spring Boot runs this file automatically. Because Hibernate creates the tables
-- for us, we set spring.jpa.defer-datasource-initialization=true in
-- application.properties so this runs AFTER the tables exist.
--
-- This replaces the old hard-coded List.of(...) data that used to live in the
-- repository classes.

INSERT INTO organizers (id, name, description) VALUES
    (101, 'Wisetech Global', 'Technology solutions provider'),
    (102, 'Tech Solutions', 'Technology solutions specialist'),
    (103, 'Event Pro', 'Event management company');

INSERT INTO venues (id, name, street, city, country) VALUES
    (201, 'Olympic Park', '1 Olympic Boulevard', 'Sydney', 'Australia'),
    (202, 'Convention Centre', '14 Darling Drive', 'Sydney', 'Australia'),
    (203, 'Tech Arena', '88 Innovation Way', 'Melbourne', 'Australia');

INSERT INTO events (id, name, organizer_id, venue_id, start_date, end_date) VALUES
    (301, 'Spring Conference', 101, 202, DATE '2026-09-10', DATE '2026-09-12'),
    (302, 'Tech Summit', 103, 201, DATE '2026-10-05', DATE '2026-10-07'),
    (303, 'Developer Day', 101, 201, DATE '2026-11-01', DATE '2026-11-01');

INSERT INTO products (id, event_id, name, description, price) VALUES
    (401, 301, 'General Admission', 'Standard entry ticket', 99.00),
    (402, 301, 'VIP Pass', 'VIP entry with premium seating', 249.00),
    (403, 302, 'Day Pass', 'Single day access', 149.00),
    (404, 303, 'Workshop Ticket', 'Hands-on developer workshop', 79.00);
