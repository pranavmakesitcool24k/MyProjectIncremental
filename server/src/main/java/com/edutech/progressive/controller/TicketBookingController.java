package com.edutech.progressive.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.progressive.entity.TicketBooking;
import com.edutech.progressive.service.impl.TicketBookingServiceImpl;

@RestController
@RequestMapping("/ticket")
public class TicketBookingController {

    private TicketBookingServiceImpl ticketBookingService;

    public TicketBookingController(TicketBookingServiceImpl ticketBookingService) {
        this.ticketBookingService = ticketBookingService;
    }

    @GetMapping
    public ResponseEntity<List<TicketBooking>> getAllBookings() {
        try {
            return new ResponseEntity<>(ticketBookingService.getAllTicketBookings(), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Integer> createBooking(@RequestBody TicketBooking ticketBooking) {
        try {
            int id = ticketBookingService.createBooking(ticketBooking);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable int bookingId) {
        try {
            ticketBookingService.cancelBooking(bookingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<TicketBooking>> getBookingsByUserEmail(@PathVariable String email) {
        try {
            return new ResponseEntity<>(ticketBookingService.getBookingsByUserEmail(email), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
