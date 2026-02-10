package com.example.cinema_learn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "films")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_seq")
    @SequenceGenerator(name = "film_seq", sequenceName = "film_seq", allocationSize = 1)
    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "film_name", nullable = false)
    private String filmName;

    @Column(name = "film_genre", nullable = false)
    private String filmGenre;

    @Column(name = "film_duration", nullable = false)
    private Integer filmDuration;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ticket> tickets;
}
