package com.cinema.booking;

public class Ticket {
    private int id;
    private Movie movie;
    private String seatNumber;
    private double price;

    public Ticket(int id, Movie movie, String seatNumber, double price) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        if (seatNumber == null || seatNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Seat number cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.id = id;
        this.movie = movie;
        this.seatNumber = seatNumber.trim();
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", movie=" + movie.getTitle() +
                ", seatNumber='" + seatNumber + '\'' +
                ", price=" + price +
                '}';
    }
}
