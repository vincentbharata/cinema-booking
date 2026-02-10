package com.example.cinema_learn.controller;

import org.springframework.web.bind.annotation.*;

import com.example.cinema_learn.dto.BookingRequest;
import com.example.cinema_learn.service.BookingService;
import com.example.cinema_learn.util.ApiResponse;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(true, "Success", bookingService.findAll());
    }

    @GetMapping("/{bookingId}")
    public ApiResponse<?> getById(@PathVariable Long bookingId) {
        return new ApiResponse<>(true, "Success", bookingService.findById(bookingId));
    }

    @PostMapping
    public ApiResponse<?> book(@RequestBody BookingRequest req) {
        return new ApiResponse<>(true, "Booking success", bookingService.book(req));
    }

    @PutMapping("/{bookingId}")
    public ApiResponse<?> update(@PathVariable Long bookingId, @RequestBody BookingRequest req) {
        return new ApiResponse<>(true, "Booking updated", bookingService.update(bookingId, req));
    }

    @DeleteMapping("/{bookingId}")
    public ApiResponse<?> delete(@PathVariable Long bookingId) {
        bookingService.delete(bookingId);
        return new ApiResponse<>(true, "Booking deleted", null);
    }
}
