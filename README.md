# Cinema Booking System

A simple Java-based cinema booking system that manages movies, tickets, and bookings.

## Features

- **Movie Management**: Create and manage movie information including title, duration, and genre
- **Ticket Management**: Create tickets for specific movies with seat assignments and pricing
- **Booking Management**: Handle customer bookings with multiple tickets and automatic price calculation

## Project Structure

```
src/
├── main/java/com/cinema/booking/
│   ├── Movie.java              # Movie entity class
│   ├── Ticket.java             # Ticket entity class
│   ├── Booking.java            # Booking entity class
│   └── CinemaBookingDemo.java  # Demo application
└── test/java/com/cinema/booking/
    ├── MovieTest.java          # Unit tests for Movie
    ├── TicketTest.java         # Unit tests for Ticket
    └── BookingTest.java        # Unit tests for Booking
```

## Building the Project

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Compile
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Run Demo
```bash
mvn exec:java -Dexec.mainClass="com.cinema.booking.CinemaBookingDemo"
```

## Usage Example

```java
// Create a movie
Movie movie = new Movie(1, "The Matrix", 136, "Sci-Fi");

// Create tickets
Ticket ticket1 = new Ticket(1, movie, "A1", 12.50);
Ticket ticket2 = new Ticket(2, movie, "A2", 12.50);

// Create a booking
Booking booking = new Booking(1, "John Doe");
booking.addTicket(ticket1);
booking.addTicket(ticket2);

// Get booking details
System.out.println("Customer: " + booking.getCustomerName());
System.out.println("Total Tickets: " + booking.getTicketCount());
System.out.println("Total Price: $" + booking.getTotalPrice());
```

## Classes

### Movie
Represents a movie showing in the cinema.
- `id`: Unique identifier
- `title`: Movie title (required)
- `durationMinutes`: Duration in minutes (must be positive)
- `genre`: Movie genre

### Ticket
Represents a ticket for a specific movie and seat.
- `id`: Unique identifier
- `movie`: The movie for this ticket (required)
- `seatNumber`: Seat assignment (required)
- `price`: Ticket price (cannot be negative)

### Booking
Represents a customer's booking with one or more tickets.
- `id`: Unique identifier
- `customerName`: Name of the customer (required)
- `tickets`: List of tickets in this booking
- `totalPrice`: Automatically calculated sum of ticket prices

## Test Coverage

The project includes comprehensive unit tests:
- 5 tests for Movie class
- 6 tests for Ticket class
- 8 tests for Booking class

Run `mvn test` to execute all tests.