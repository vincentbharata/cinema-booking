-- Insert Sample Films
INSERT INTO films (film_id, film_name, film_genre, film_duration) VALUES (1, 'Avengers Endgame', 'Action', 181);
INSERT INTO films (film_id, film_name, film_genre, film_duration) VALUES (2, 'The Lion King', 'Animation', 118);
INSERT INTO films (film_id, film_name, film_genre, film_duration) VALUES (3, 'Inception', 'Sci-Fi', 148);

-- Insert Sample Tickets
INSERT INTO tickets (ticket_id, film_id, ticket_price, ticket_quota) VALUES (1, 1, 150000, 50);
INSERT INTO tickets (ticket_id, film_id, ticket_price, ticket_quota) VALUES (2, 1, 100000, 50);
INSERT INTO tickets (ticket_id, film_id, ticket_price, ticket_quota) VALUES (3, 2, 120000, 60);
INSERT INTO tickets (ticket_id, film_id, ticket_price, ticket_quota) VALUES (4, 3, 150000, 40);

-- Insert Sample Bookings
INSERT INTO bookings (booking_id, ticket_id, booking_amount, booking_total_price) VALUES (1, 1, 2, 300000);
INSERT INTO bookings (booking_id, ticket_id, booking_amount, booking_total_price) VALUES (2, 2, 3, 300000);