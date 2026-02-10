package com.example.cinema_learn.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cinema_learn.dto.FilmRequest;
import com.example.cinema_learn.entity.Film;
import com.example.cinema_learn.exception.BusinessException;
import com.example.cinema_learn.repository.FilmRepository;
import com.example.cinema_learn.util.QueryDSLUtil;
import com.querydsl.core.types.Predicate;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Film findById(Long filmId) {
        return filmRepository.findById(filmId)
                .orElseThrow(() -> new BusinessException("Film not found"));
    }

    /**
     * Search films by name menggunakan QueryDSL
     */
    public List<Film> searchByName(String name) {
        Predicate predicate = QueryDSLUtil.filmByName(name);
        return predicate != null ? StreamSupport.stream(filmRepository.findAll(predicate).spliterator(), false).toList() : filmRepository.findAll();
    }

    /**
     * Search films by genre menggunakan QueryDSL
     */
    public List<Film> searchByGenre(String genre) {
        Predicate predicate = QueryDSLUtil.filmByGenre(genre);
        return predicate != null ? StreamSupport.stream(filmRepository.findAll(predicate).spliterator(), false).toList() : filmRepository.findAll();
    }

    /**
     * Search films by minimum duration menggunakan QueryDSL
     */
    public List<Film> searchByMinDuration(Integer minDuration) {
        Predicate predicate = QueryDSLUtil.filmByDurationGreaterThan(minDuration);
        return predicate != null ? StreamSupport.stream(filmRepository.findAll(predicate).spliterator(), false).toList() : filmRepository.findAll();
    }

    public Film create(FilmRequest req) {
        if (req == null || req.getFilmName() == null || req.getFilmName().trim().isEmpty()) {
            throw new BusinessException("Invalid film request");
        }

        Film film = new Film();
        film.setFilmName(req.getFilmName());
        film.setFilmGenre(req.getFilmGenre());
        film.setFilmDuration(req.getFilmDuration());

        return filmRepository.save(film);
    }

    public Film update(Long filmId, FilmRequest req) {
        if (req == null || req.getFilmName() == null || req.getFilmName().trim().isEmpty()) {
            throw new BusinessException("Invalid film request");
        }

        Film film = findById(filmId);
        film.setFilmName(req.getFilmName());
        film.setFilmGenre(req.getFilmGenre());
        film.setFilmDuration(req.getFilmDuration());
        return filmRepository.save(film);
    }

    public void delete(Long filmId) {
        Film film = findById(filmId);
        filmRepository.delete(film);
    }
}
