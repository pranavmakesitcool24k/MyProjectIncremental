package com.edutech.progressive.service;

import java.sql.SQLException;
import java.util.List;

import com.edutech.progressive.entity.Match;

public interface MatchService {

    List<Match> getAllMatches() throws SQLException;

    Match getMatchById(int matchId) throws SQLException;

    Integer addMatch(Match match) throws SQLException;

    void updateMatch(Match match) throws SQLException;

    void deleteMatch(int matchId) throws SQLException;

    default List<Match> getAllMatchesByStatus(String status) throws SQLException {
        return null;
    }
}