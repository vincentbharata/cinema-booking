package com.example.cinema_learn.util;

import com.example.cinema_learn.entity.QFilm;
import com.example.cinema_learn.entity.QTicket;
import com.example.cinema_learn.entity.QBooking;
import com.querydsl.core.types.Predicate;

/**
 * Utility class untuk membangun QueryDSL predicates Contoh penggunaan type-safe
 * queries dengan QueryDSL
 */
public class QueryDSLUtil {

    // Film Predicates
    public static Predicate filmByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return QFilm.film.filmName.containsIgnoreCase(name);
    }

    public static Predicate filmByGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            return null;
        }
        return QFilm.film.filmGenre.containsIgnoreCase(genre);
    }

    public static Predicate filmByDurationGreaterThan(Integer duration) {
        if (duration == null || duration <= 0) {
            return null;
        }
        return QFilm.film.filmDuration.gt(duration);
    }

    // Ticket Predicates
    public static Predicate ticketByPriceRange(Double minPrice, Double maxPrice) {
        com.querydsl.core.types.dsl.BooleanExpression predicate = null;
        if (minPrice != null && minPrice > 0) {
            predicate = QTicket.ticket.ticketPrice.goe(minPrice);
        }
        if (maxPrice != null && maxPrice > 0) {
            if (predicate != null) {
                predicate = predicate.and(QTicket.ticket.ticketPrice.loe(maxPrice));
            } else {
                predicate = QTicket.ticket.ticketPrice.loe(maxPrice);
            }
        }
        return predicate;
    }

    public static Predicate ticketByQuotaAvailable() {
        return QTicket.ticket.ticketQuota.gt(0);
    }

    public static Predicate ticketByFilmId(Long filmId) {
        if (filmId == null || filmId <= 0) {
            return null;
        }
        return QTicket.ticket.film.filmId.eq(filmId);
    }

    // Booking Predicates
    public static Predicate bookingByAmountGreaterThan(Integer amount) {
        if (amount == null || amount <= 0) {
            return null;
        }
        return QBooking.booking.bookingAmount.gt(amount);
    }

    public static Predicate bookingByTotalPriceRange(Double minPrice, Double maxPrice) {
        com.querydsl.core.types.dsl.BooleanExpression predicate = null;
        if (minPrice != null && minPrice > 0) {
            predicate = QBooking.booking.bookingTotalPrice.goe(minPrice);
        }
        if (maxPrice != null && maxPrice > 0) {
            if (predicate != null) {
                predicate = predicate.and(QBooking.booking.bookingTotalPrice.loe(maxPrice));
            } else {
                predicate = QBooking.booking.bookingTotalPrice.loe(maxPrice);
            }
        }
        return predicate;
    }

    public static Predicate bookingByTicketId(Long ticketId) {
        if (ticketId == null || ticketId <= 0) {
            return null;
        }
        return QBooking.booking.ticket.ticketId.eq(ticketId);
    }
}
