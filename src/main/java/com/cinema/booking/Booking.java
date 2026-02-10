package com.cinema.booking;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private int id;
    private String customerName;
    private List<Ticket> tickets;
    private double totalPrice;

    public Booking(int id, String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        this.id = id;
        this.customerName = customerName;
        this.tickets = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }
        tickets.add(ticket);
        totalPrice += ticket.getPrice();
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getTicketCount() {
        return tickets.size();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", ticketCount=" + tickets.size() +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
