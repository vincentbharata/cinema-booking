package com.example.cinema_learn.controller;

import org.springframework.web.bind.annotation.*;

import com.example.cinema_learn.dto.FilmRequest;
import com.example.cinema_learn.service.FilmService;
import com.example.cinema_learn.util.ApiResponse;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(true, "Success", filmService.findAll());
    }

    @GetMapping("/{filmId}")
    public ApiResponse<?> getById(@PathVariable Long filmId) {
        return new ApiResponse<>(true, "Success", filmService.findById(filmId));
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody FilmRequest req) {
        return new ApiResponse<>(true, "Film created", filmService.create(req));
    }

    @PutMapping("/{filmId}")
    public ApiResponse<?> update(@PathVariable Long filmId, @RequestBody FilmRequest req) {
        return new ApiResponse<>(true, "Film updated", filmService.update(filmId, req));
    }

    @DeleteMapping("/{filmId}")
    public ApiResponse<?> delete(@PathVariable Long filmId) {
        filmService.delete(filmId);
        return new ApiResponse<>(true, "Film deleted", null);
    }
}
