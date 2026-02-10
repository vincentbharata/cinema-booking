package com.cinema.booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    @Test
    public void testMovieCreation() {
        Movie movie = new Movie(1, "The Matrix", 136, "Sci-Fi");
        
        assertEquals(1, movie.getId());
        assertEquals("The Matrix", movie.getTitle());
        assertEquals(136, movie.getDurationMinutes());
        assertEquals("Sci-Fi", movie.getGenre());
    }

    @Test
    public void testMovieWithNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Movie(1, null, 120, "Action");
        });
    }

    @Test
    public void testMovieWithEmptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Movie(1, "  ", 120, "Action");
        });
    }

    @Test
    public void testMovieWithInvalidDuration() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Movie(1, "Test Movie", 0, "Drama");
        });
    }

    @Test
    public void testMovieToString() {
        Movie movie = new Movie(1, "Inception", 148, "Thriller");
        String result = movie.toString();
        
        assertTrue(result.contains("Inception"));
        assertTrue(result.contains("148"));
        assertTrue(result.contains("Thriller"));
    }
}
