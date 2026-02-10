package com.example.cinema_learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.example.cinema_learn.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, QuerydslPredicateExecutor<Ticket> {
}
