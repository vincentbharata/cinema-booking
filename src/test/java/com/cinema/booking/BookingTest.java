package com.cinema.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
    
    private Movie movie;
    private Ticket ticket1;
    private Ticket ticket2;

    @BeforeEach
    public void setUp() {
        movie = new Movie(1, "The Matrix", 136, "Sci-Fi");
        ticket1 = new Ticket(1, movie, "A1", 12.50);
        ticket2 = new Ticket(2, movie, "A2", 12.50);
    }

    @Test
    public void testBookingCreation() {
        Booking booking = new Booking(1, "John Doe");
        
        assertEquals(1, booking.getId());
        assertEquals("John Doe", booking.getCustomerName());
        assertEquals(0, booking.getTicketCount());
        assertEquals(0.0, booking.getTotalPrice(), 0.001);
    }

    @Test
    public void testBookingWithNullCustomerName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Booking(1, null);
        });
    }

    @Test
    public void testBookingWithEmptyCustomerName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Booking(1, "  ");
        });
    }

    @Test
    public void testAddTicket() {
        Booking booking = new Booking(1, "Jane Smith");
        booking.addTicket(ticket1);
        
        assertEquals(1, booking.getTicketCount());
        assertEquals(12.50, booking.getTotalPrice(), 0.001);
    }

    @Test
    public void testAddMultipleTickets() {
        Booking booking = new Booking(1, "Jane Smith");
        booking.addTicket(ticket1);
        booking.addTicket(ticket2);
        
        assertEquals(2, booking.getTicketCount());
        assertEquals(25.00, booking.getTotalPrice(), 0.001);
    }

    @Test
    public void testAddNullTicket() {
        Booking booking = new Booking(1, "Jane Smith");
        
        assertThrows(IllegalArgumentException.class, () -> {
            booking.addTicket(null);
        });
    }

    @Test
    public void testGetTicketsReturnsNewList() {
        Booking booking = new Booking(1, "Jane Smith");
        booking.addTicket(ticket1);
        
        assertEquals(1, booking.getTickets().size());
        
        // Modifying returned list should not affect booking
        booking.getTickets().clear();
        assertEquals(1, booking.getTicketCount());
    }

    @Test
    public void testBookingToString() {
        Booking booking = new Booking(1, "John Doe");
        booking.addTicket(ticket1);
        String result = booking.toString();
        
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("12.5"));
    }
}
