package com.example.cinema_learn.controller;

import org.springframework.web.bind.annotation.*;

import com.example.cinema_learn.dto.TicketRequest;
import com.example.cinema_learn.service.TicketService;
import com.example.cinema_learn.util.ApiResponse;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(true, "Success", ticketService.findAll());
    }

    @GetMapping("/{ticketId}")
    public ApiResponse<?> getById(@PathVariable Long ticketId) {
        return new ApiResponse<>(true, "Success", ticketService.findById(ticketId));
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody TicketRequest req) {
        return new ApiResponse<>(true, "Ticket created", ticketService.create(req));
    }

    @PutMapping("/{ticketId}")
    public ApiResponse<?> update(@PathVariable Long ticketId, @RequestBody TicketRequest req) {
        return new ApiResponse<>(true, "Ticket updated", ticketService.update(ticketId, req));
    }

    @DeleteMapping("/{ticketId}")
    public ApiResponse<?> delete(@PathVariable Long ticketId) {
        ticketService.delete(ticketId);
        return new ApiResponse<>(true, "Ticket deleted", null);
    }
}
