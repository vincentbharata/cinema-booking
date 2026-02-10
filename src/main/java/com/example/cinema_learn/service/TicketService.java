package com.example.cinema_learn.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cinema_learn.dto.TicketRequest;
import com.example.cinema_learn.entity.Film;
import com.example.cinema_learn.entity.Ticket;
import com.example.cinema_learn.exception.BusinessException;
import com.example.cinema_learn.repository.FilmRepository;
import com.example.cinema_learn.repository.TicketRepository;
import com.example.cinema_learn.util.QueryDSLUtil;
import com.querydsl.core.types.Predicate;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FilmRepository filmRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new BusinessException("Ticket not found"));
    }

    /**
     * Search tickets dengan price range menggunakan QueryDSL
     */
    public List<Ticket> searchByPriceRange(Double minPrice, Double maxPrice) {
        Predicate predicate = QueryDSLUtil.ticketByPriceRange(minPrice, maxPrice);
        return predicate != null ? StreamSupport.stream(ticketRepository.findAll(predicate).spliterator(), false).toList() : ticketRepository.findAll();
    }

    /**
     * Find tickets yang masih tersedia (quota > 0) menggunakan QueryDSL
     */
    public List<Ticket> findAvailableTickets() {
        Predicate predicate = QueryDSLUtil.ticketByQuotaAvailable();
        return predicate != null ? StreamSupport.stream(ticketRepository.findAll(predicate).spliterator(), false).toList() : ticketRepository.findAll();
    }

    /**
     * Find tickets untuk film tertentu menggunakan QueryDSL
     */
    public List<Ticket> findByFilm(Long filmId) {
        Predicate predicate = QueryDSLUtil.ticketByFilmId(filmId);
        return predicate != null ? StreamSupport.stream(ticketRepository.findAll(predicate).spliterator(), false).toList() : ticketRepository.findAll();
    }

    public Ticket create(TicketRequest req) {
        if (req == null || req.getFilmId() == null || req.getTicketPrice() == null || req.getTicketQuota() == null) {
            throw new BusinessException("Invalid ticket request");
        }

        Film film = filmRepository.findById(req.getFilmId())
                .orElseThrow(() -> new BusinessException("Film not found"));

        Ticket ticket = new Ticket();
        ticket.setFilm(film);
        ticket.setTicketPrice(req.getTicketPrice());
        ticket.setTicketQuota(req.getTicketQuota());

        return ticketRepository.save(ticket);
    }

    public Ticket update(Long ticketId, TicketRequest req) {
        if (req == null || req.getFilmId() == null || req.getTicketPrice() == null || req.getTicketQuota() == null) {
            throw new BusinessException("Invalid ticket request");
        }

        Ticket ticket = findById(ticketId);
        Film film = filmRepository.findById(req.getFilmId())
                .orElseThrow(() -> new BusinessException("Film not found"));

        ticket.setFilm(film);
        ticket.setTicketPrice(req.getTicketPrice());
        ticket.setTicketQuota(req.getTicketQuota());
        return ticketRepository.save(ticket);
    }

    public void delete(Long ticketId) {
        Ticket ticket = findById(ticketId);
        ticketRepository.delete(ticket);
    }
}
