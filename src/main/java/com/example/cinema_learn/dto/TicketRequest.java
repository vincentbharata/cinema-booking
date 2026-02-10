package com.example.cinema_learn.dto;

import lombok.Data;

@Data
public class TicketRequest {
    private Long filmId;
    private Double ticketPrice;
    private Integer ticketQuota;
}
