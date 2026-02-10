package com.cinema.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {
    
    private Movie movie;

    @BeforeEach
    public void setUp() {
        movie = new Movie(1, "The Matrix", 136, "Sci-Fi");
    }

    @Test
    public void testTicketCreation() {
        Ticket ticket = new Ticket(1, movie, "A1", 12.50);
        
        assertEquals(1, ticket.getId());
        assertEquals(movie, ticket.getMovie());
        assertEquals("A1", ticket.getSeatNumber());
        assertEquals(12.50, ticket.getPrice(), 0.001);
    }

    @Test
    public void testTicketWithNullMovie() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, null, "A1", 12.50);
        });
    }

    @Test
    public void testTicketWithNullSeatNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, movie, null, 12.50);
        });
    }

    @Test
    public void testTicketWithEmptySeatNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, movie, "  ", 12.50);
        });
    }

    @Test
    public void testTicketWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, movie, "A1", -5.0);
        });
    }

    @Test
    public void testTicketToString() {
        Ticket ticket = new Ticket(1, movie, "B5", 15.00);
        String result = ticket.toString();
        
        assertTrue(result.contains("The Matrix"));
        assertTrue(result.contains("B5"));
        assertTrue(result.contains("15.0"));
    }
}
