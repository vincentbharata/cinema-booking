package com.cinema.booking;

public class CinemaBookingDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Cinema Booking System Demo ===\n");
        
        // Create movies
        Movie movie1 = new Movie(1, "The Matrix", 136, "Sci-Fi");
        Movie movie2 = new Movie(2, "Inception", 148, "Thriller");
        
        System.out.println("Available Movies:");
        System.out.println(movie1);
        System.out.println(movie2);
        System.out.println();
        
        // Create tickets
        Ticket ticket1 = new Ticket(1, movie1, "A1", 12.50);
        Ticket ticket2 = new Ticket(2, movie1, "A2", 12.50);
        Ticket ticket3 = new Ticket(3, movie2, "B5", 15.00);
        
        System.out.println("Created Tickets:");
        System.out.println(ticket1);
        System.out.println(ticket2);
        System.out.println(ticket3);
        System.out.println();
        
        // Create bookings
        Booking booking1 = new Booking(1, "John Doe");
        booking1.addTicket(ticket1);
        booking1.addTicket(ticket2);
        
        Booking booking2 = new Booking(2, "Jane Smith");
        booking2.addTicket(ticket3);
        
        System.out.println("Bookings:");
        System.out.println(booking1);
        System.out.println("  Tickets: " + booking1.getTicketCount());
        System.out.println("  Total Price: $" + booking1.getTotalPrice());
        System.out.println();
        
        System.out.println(booking2);
        System.out.println("  Tickets: " + booking2.getTicketCount());
        System.out.println("  Total Price: $" + booking2.getTotalPrice());
        System.out.println();
        
        System.out.println("=== Demo Complete ===");
    }
}
