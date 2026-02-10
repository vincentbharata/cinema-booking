-- Create Cinema Schema
CREATE SCHEMA cinema;

-- Create Sequences in cinema schema
CREATE SEQUENCE cinema.film_seq START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE cinema.ticket_seq START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE cinema.booking_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- Create Films Table in cinema schema
CREATE TABLE cinema.films (
    film_id NUMBER PRIMARY KEY,
    film_name VARCHAR2(255) NOT NULL,
    film_genre VARCHAR2(100),
    film_duration NUMBER NOT NULL
);

-- Create Tickets Table in cinema schema
CREATE TABLE cinema.tickets (
    ticket_id NUMBER PRIMARY KEY,
    film_id NUMBER NOT NULL,
    ticket_price NUMBER(10,2) NOT NULL,
    ticket_quota NUMBER NOT NULL,
    CONSTRAINT fk_ticket_film FOREIGN KEY (film_id) REFERENCES cinema.films(film_id) ON DELETE CASCADE
);

-- Create Bookings Table in cinema schema
CREATE TABLE cinema.bookings (
    booking_id NUMBER PRIMARY KEY,
    ticket_id NUMBER NOT NULL,
    booking_amount NUMBER NOT NULL,
    booking_total_price NUMBER(10,2) NOT NULL,
    CONSTRAINT fk_booking_ticket FOREIGN KEY (ticket_id) REFERENCES cinema.tickets(ticket_id) ON DELETE CASCADE
);

-- Insert Sample Data into cinema schema
INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration) VALUES (cinema.film_seq.NEXTVAL, 'Avengers Endgame', 'Action', 181);
INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration) VALUES (cinema.film_seq.NEXTVAL, 'The Lion King', 'Animation', 118);
INSERT INTO cinema.films (film_id, film_name, film_genre, film_duration) VALUES (cinema.film_seq.NEXTVAL, 'Inception', 'Sci-Fi', 148);

INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota) VALUES (cinema.ticket_seq.NEXTVAL, 1, 150000, 50);
INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota) VALUES (cinema.ticket_seq.NEXTVAL, 1, 100000, 50);
INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota) VALUES (cinema.ticket_seq.NEXTVAL, 2, 120000, 60);
INSERT INTO cinema.tickets (ticket_id, film_id, ticket_price, ticket_quota) VALUES (cinema.ticket_seq.NEXTVAL, 3, 150000, 40);

INSERT INTO cinema.bookings (booking_id, ticket_id, booking_amount, booking_total_price) VALUES (cinema.booking_seq.NEXTVAL, 1, 2, 300000);
INSERT INTO cinema.bookings (booking_id, ticket_id, booking_amount, booking_total_price) VALUES (cinema.booking_seq.NEXTVAL, 2, 3, 300000);

COMMIT;
