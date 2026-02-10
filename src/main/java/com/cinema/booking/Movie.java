package com.cinema.booking;

public class Movie {
    private int id;
    private String title;
    private int durationMinutes;
    private String genre;

    public Movie(int id, String title, int durationMinutes, String genre) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be null or empty");
        }
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Movie duration must be positive");
        }
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", genre='" + genre + '\'' +
                '}';
    }
}
