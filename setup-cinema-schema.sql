-- =============================================================================
-- SETUP SCHEMA CINEMA DI ORACLE 11g
-- =============================================================================
-- Execute sebagai user SYSTEM di SQL*Plus atau SQL Developer

-- 1. Buat User CINEMA (acts as schema owner in Oracle 11g)
BEGIN
  EXECUTE IMMEDIATE 'CREATE USER cinema IDENTIFIED BY cinema123 DEFAULT TABLESPACE users QUOTA UNLIMITED ON users';
  DBMS_OUTPUT.PUT_LINE('User CINEMA created successfully');
EXCEPTION WHEN OTHERS THEN
  IF SQLCODE = -955 THEN
    DBMS_OUTPUT.PUT_LINE('User CINEMA already exists');
  ELSE
    RAISE;
  END IF;
END;
/

-- 2. Grant privileges ke user CINEMA
GRANT CONNECT, RESOURCE TO cinema;
GRANT CREATE SEQUENCE, CREATE TABLE, CREATE INDEX TO cinema;
GRANT UNLIMITED TABLESPACE TO cinema;
GRANT DROP ANY TABLE TO cinema;
GRANT DROP ANY SEQUENCE TO cinema;

-- 3. Create sequences di CINEMA schema
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

-- 4. Create tables di CINEMA schema
CREATE TABLE cinema.films (
  film_id          NUMBER(19) PRIMARY KEY,
  film_name        VARCHAR2(255) NOT NULL,
  film_genre       VARCHAR2(100),
  film_duration    NUMBER(10),
  created_date     TIMESTAMP DEFAULT SYSTIMESTAMP,
  updated_date     TIMESTAMP
);

CREATE TABLE cinema.tickets (
  ticket_id        NUMBER(19) PRIMARY KEY,
  film_id          NUMBER(19) NOT NULL,
  ticket_price     NUMBER(10, 2) NOT NULL,
  ticket_quota     NUMBER(10) NOT NULL,
  created_date     TIMESTAMP DEFAULT SYSTIMESTAMP,
  updated_date     TIMESTAMP,
  CONSTRAINT fk_tickets_films FOREIGN KEY (film_id) REFERENCES cinema.films(film_id)
);

CREATE TABLE cinema.bookings (
  booking_id        NUMBER(19) PRIMARY KEY,
  ticket_id         NUMBER(19) NOT NULL,
  booking_amount    NUMBER(10) NOT NULL,
  booking_total_price NUMBER(10, 2) NOT NULL,
  created_date      TIMESTAMP DEFAULT SYSTIMESTAMP,
  updated_date      TIMESTAMP,
  CONSTRAINT fk_bookings_tickets FOREIGN KEY (ticket_id) REFERENCES cinema.tickets(ticket_id)
);

-- 5. Create indexes
CREATE INDEX cinema.idx_tickets_film_id ON cinema.tickets(film_id);
CREATE INDEX cinema.idx_bookings_ticket_id ON cinema.bookings(ticket_id);

-- 6. Insert sample data
INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration)
VALUES (cinema.film_seq.NEXTVAL, 'The Shawshank Redemption', 'Drama', 142);

INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration)
VALUES (cinema.film_seq.NEXTVAL, 'The Dark Knight', 'Action', 152);

INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration)
VALUES (cinema.film_seq.NEXTVAL, 'Inception', 'Sci-Fi', 148);

INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota)
VALUES (cinema.ticket_seq.NEXTVAL, 1, 150000.00, 100);

INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota)
VALUES (cinema.ticket_seq.NEXTVAL, 2, 175000.00, 150);

INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota)
VALUES (cinema.ticket_seq.NEXTVAL, 3, 160000.00, 120);

COMMIT;

-- 7. Verify data
SELECT COUNT(*) as total_films FROM cinema.films;
SELECT COUNT(*) as total_tickets FROM cinema.tickets;
SELECT COUNT(*) as total_bookings FROM cinema.bookings;

-- 8. Check tables and sequences in CINEMA schema
SELECT table_name FROM user_tables WHERE owner = 'CINEMA' ORDER BY table_name;
SELECT sequence_name FROM user_sequences WHERE sequence_owner = 'CINEMA' ORDER BY sequence_name;
