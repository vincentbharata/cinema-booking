package com.example.cinema_learn.dto;

import lombok.Data;

@Data
public class FilmRequest {
    private String filmName;
    private String filmGenre;
    private Integer filmDuration;
}
