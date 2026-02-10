-- Create CINEMA Schema and Tables
-- Execute this script with SQL*Plus or SQL Developer with SYSTEM user

-- Drop existing objects (optional - uncomment if needed)
-- DROP SCHEMA cinema CASCADE;
-- BEGIN
--   EXECUTE IMMEDIATE 'DROP SEQUENCE cinema.film_seq';
-- EXCEPTION WHEN OTHERS THEN NULL;
-- END;
-- /

-- Create CINEMA Schema
CREATE SCHEMA CINEMA;

-- Create Sequences
CREATE SEQUENCE cinema.film_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE;

CREATE SEQUENCE cinema.ticket_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE;

CREATE SEQUENCE cinema.booking_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE;

-- Create FILMS table
CREATE TABLE cinema.films (
  film_id          NUMBER(19) PRIMARY KEY,
  film_name        VARCHAR2(255) NOT NULL,
  film_genre       VARCHAR2(100),
  film_duration    NUMBER(10),
  created_date     TIMESTAMP DEFAULT SYSTIMESTAMP,
  updated_date     TIMESTAMP
);

-- Create TICKETS table
CREATE TABLE cinema.tickets (
  ticket_id        NUMBER(19) PRIMARY KEY,
  film_id          NUMBER(19) NOT NULL,
  ticket_price     NUMBER(10, 2) NOT NULL,
  ticket_quota     NUMBER(10) NOT NULL,
  created_date     TIMESTAMP DEFAULT SYSTIMESTAMP,
  updated_date     TIMESTAMP,
  CONSTRAINT fk_tickets_films FOREIGN KEY (film_id) REFERENCES cinema.films(film_id)
);

-- Create BOOKINGS table
CREATE TABLE cinema.bookings (
  booking_id        NUMBER(19) PRIMARY KEY,
  ticket_id         NUMBER(19) NOT NULL,
  booking_amount    NUMBER(10) NOT NULL,
  booking_total_price NUMBER(10, 2) NOT NULL,
  created_date      TIMESTAMP DEFAULT SYSTIMESTAMP,
  updated_date      TIMESTAMP,
  CONSTRAINT fk_bookings_tickets FOREIGN KEY (ticket_id) REFERENCES cinema.tickets(ticket_id)
);

-- Create Indexes
CREATE INDEX idx_tickets_film_id ON cinema.tickets(film_id);
CREATE INDEX idx_bookings_ticket_id ON cinema.bookings(ticket_id);

-- Insert Sample Data
INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration)
VALUES (cinema.film_seq.NEXTVAL, 'The Shawshank Redemption', 'Drama', 142);

INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration)
VALUES (cinema.film_seq.NEXTVAL, 'The Dark Knight', 'Action', 152);

INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration)
VALUES (cinema.film_seq.NEXTVAL, 'Inception', 'Sci-Fi', 148);

COMMIT;

-- Insert Sample Tickets
INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota)
VALUES (cinema.ticket_seq.NEXTVAL, 1, 150000.00, 100);

INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota)
VALUES (cinema.ticket_seq.NEXTVAL, 2, 175000.00, 150);

INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota)
VALUES (cinema.ticket_seq.NEXTVAL, 3, 160000.00, 120);

COMMIT;

-- Verify the tables exist
SELECT table_name FROM user_tables WHERE owner = 'CINEMA';
