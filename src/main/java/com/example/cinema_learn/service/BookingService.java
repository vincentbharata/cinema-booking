package com.example.cinema_learn.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cinema_learn.dto.BookingRequest;
import com.example.cinema_learn.entity.Booking;
import com.example.cinema_learn.entity.Ticket;
import com.example.cinema_learn.exception.BusinessException;
import com.example.cinema_learn.repository.BookingRepository;
import com.example.cinema_learn.repository.TicketRepository;
import com.example.cinema_learn.util.QueryDSLUtil;
import com.querydsl.core.types.Predicate;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BusinessException("Booking not found"));
    }

    /**
     * Search bookings dengan amount lebih besar dari nilai tertentu menggunakan QueryDSL
     */
    public List<Booking> findByMinAmount(Integer minAmount) {
        Predicate predicate = QueryDSLUtil.bookingByAmountGreaterThan(minAmount);
        return predicate != null ? StreamSupport.stream(bookingRepository.findAll(predicate).spliterator(), false).toList() : bookingRepository.findAll();
    }

    /**
     * Search bookings dengan total price range menggunakan QueryDSL
     */
    public List<Booking> findByTotalPriceRange(Double minPrice, Double maxPrice) {
        Predicate predicate = QueryDSLUtil.bookingByTotalPriceRange(minPrice, maxPrice);
        return predicate != null ? StreamSupport.stream(bookingRepository.findAll(predicate).spliterator(), false).toList() : bookingRepository.findAll();
    }

    /**
     * Find bookings untuk ticket tertentu menggunakan QueryDSL
     */
    public List<Booking> findByTicket(Long ticketId) {
        Predicate predicate = QueryDSLUtil.bookingByTicketId(ticketId);
        return predicate != null ? StreamSupport.stream(bookingRepository.findAll(predicate).spliterator(), false).toList() : bookingRepository.findAll();
    }

    @Transactional
    public Booking book(BookingRequest req) {
        if (req == null || req.getTicketId() == null || req.getAmount() == null) {
            throw new BusinessException("Invalid booking request");
        }

        Ticket ticket = ticketRepository.findById(req.getTicketId())
                .orElseThrow(() -> new BusinessException("Ticket not found"));

        if (ticket.getTicketQuota() < req.getAmount()) {
            throw new BusinessException("Ticket quota not enough");
        }

        ticket.setTicketQuota(ticket.getTicketQuota() - req.getAmount());
        ticketRepository.save(ticket);

        Booking booking = new Booking();
        booking.setTicket(ticket);
        booking.setBookingAmount(req.getAmount());
        booking.setBookingTotalPrice(req.getAmount() * ticket.getTicketPrice());

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking update(Long bookingId, BookingRequest req) {
        if (req == null || req.getTicketId() == null || req.getAmount() == null) {
            throw new BusinessException("Invalid booking request");
        }

        Booking booking = findById(bookingId);
        Ticket oldTicket = booking.getTicket();

        if (oldTicket == null) {
            throw new BusinessException("Booking has no associated ticket");
        }

        Ticket ticket = ticketRepository.findById(req.getTicketId())
                .orElseThrow(() -> new BusinessException("Ticket not found"));

        // Restore old quota
        oldTicket.setTicketQuota(oldTicket.getTicketQuota() + booking.getBookingAmount());
        ticketRepository.save(oldTicket);

        // Deduct new quota
        if (ticket.getTicketQuota() < req.getAmount()) {
            throw new BusinessException("Ticket quota not enough");
        }
        ticket.setTicketQuota(ticket.getTicketQuota() - req.getAmount());
        ticketRepository.save(ticket);

        booking.setTicket(ticket);
        booking.setBookingAmount(req.getAmount());
        booking.setBookingTotalPrice(req.getAmount() * ticket.getTicketPrice());

        return bookingRepository.save(booking);
    }

    @Transactional
    public void delete(Long bookingId) {
        Booking booking = findById(bookingId);
        Ticket ticket = booking.getTicket();

        if (ticket == null) {
            throw new BusinessException("Booking has no associated ticket");
        }

        // Restore ticket quota
        ticket.setTicketQuota(ticket.getTicketQuota() + booking.getBookingAmount());
        ticketRepository.save(ticket);

        bookingRepository.delete(booking);
    }
}
