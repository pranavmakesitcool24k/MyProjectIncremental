package com.edutech.progressive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.progressive.entity.TicketBooking;

public interface TicketBookingRepository extends JpaRepository<TicketBooking, Integer> {

    List<TicketBooking> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM TicketBooking t WHERE t.match.firstTeam.teamId = ?1 OR t.match.secondTeam.teamId = ?1")
    void deleteByTeamId(int teamId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TicketBooking t WHERE t.match.matchId = ?1")
    void deleteByMatchId(int matchId);
}
