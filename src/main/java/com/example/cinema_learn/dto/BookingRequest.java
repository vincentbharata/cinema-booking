package com.example.cinema_learn.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long ticketId;
    private Integer amount;
}
