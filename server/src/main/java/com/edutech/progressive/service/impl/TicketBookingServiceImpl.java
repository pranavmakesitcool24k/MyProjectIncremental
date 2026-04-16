package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.TicketBooking;
import com.edutech.progressive.repository.TicketBookingRepository;
import com.edutech.progressive.service.TicketBookingService;

@Service
public class TicketBookingServiceImpl implements TicketBookingService {

    private TicketBookingRepository ticketBookingRepository;

    public TicketBookingServiceImpl(TicketBookingRepository ticketBookingRepository) {
        this.ticketBookingRepository = ticketBookingRepository;
    }

    @Override
    public List<TicketBooking> getAllTicketBookings() throws SQLException {
        return ticketBookingRepository.findAll();
    }

    @Override
    public int createBooking(TicketBooking ticketBooking) throws SQLException {
        return ticketBookingRepository.save(ticketBooking).getBookingId();
    }

    @Override
    public void cancelBooking(int bookingId) throws SQLException {
        ticketBookingRepository.deleteById(bookingId);
    }

    @Override
    public List<TicketBooking> getBookingsByUserEmail(String email) throws SQLException {
        return ticketBookingRepository.findByEmail(email);
    }
}
