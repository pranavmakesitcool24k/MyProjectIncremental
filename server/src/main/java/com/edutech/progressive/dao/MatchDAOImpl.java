package com.edutech.progressive.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Match;

public class MatchDAOImpl implements MatchDAO {

    @Override
    public int addMatch(Match match) throws SQLException {
        String sql = "INSERT INTO matches (first_team_id, second_team_id, match_date, venue, result, status, winner_team_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, match.getFirstTeamId());
            ps.setInt(2, match.getSecondTeamId());
            ps.setDate(3, new Date(match.getMatchDate().getTime()));
            ps.setString(4, match.getVenue());
            ps.setString(5, match.getResult());
            ps.setString(6, match.getStatus());
            ps.setInt(7, match.getWinnerTeamId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public Match getMatchById(int matchId) throws SQLException {
        String sql = "SELECT match_id, first_team_id, second_team_id, match_date, venue, result, status, winner_team_id FROM matches WHERE match_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, matchId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Match(
                            rs.getInt("match_id"),
                            rs.getInt("first_team_id"),
                            rs.getInt("second_team_id"),
                            rs.getDate("match_date"),
                            rs.getString("venue"),
                            rs.getString("result"),
                            rs.getString("status"),
                            rs.getInt("winner_team_id")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void updateMatch(Match match) throws SQLException {
        Match existing = getMatchById(match.getMatchId());
        if (existing == null) return;

        int firstTeamId = match.getFirstTeamId() == 0 ? existing.getFirstTeamId() : match.getFirstTeamId();
        int secondTeamId = match.getSecondTeamId() == 0 ? existing.getSecondTeamId() : match.getSecondTeamId();
        java.util.Date matchDate = match.getMatchDate() == null ? existing.getMatchDate() : match.getMatchDate();
        String venue = match.getVenue() == null ? existing.getVenue() : match.getVenue();
        String result = match.getResult() == null ? existing.getResult() : match.getResult();
        String status = match.getStatus() == null ? existing.getStatus() : match.getStatus();
        int winnerTeamId = match.getWinnerTeamId() == 0 ? existing.getWinnerTeamId() : match.getWinnerTeamId();

        String sql = "UPDATE matches SET first_team_id=?, second_team_id=?, match_date=?, venue=?, result=?, status=?, winner_team_id=? WHERE match_id=?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, firstTeamId);
            ps.setInt(2, secondTeamId);
            ps.setDate(3, new Date(matchDate.getTime()));
            ps.setString(4, venue);
            ps.setString(5, result);
            ps.setString(6, status);
            ps.setInt(7, winnerTeamId);
            ps.setInt(8, match.getMatchId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteMatch(int matchId) throws SQLException {
        String sql = "DELETE FROM matches WHERE match_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, matchId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Match> getAllMatches() throws SQLException {
        String sql = "SELECT match_id, first_team_id, second_team_id, match_date, venue, result, status, winner_team_id FROM matches";
        List<Match> list = new ArrayList<>();
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Match(
                        rs.getInt("match_id"),
                        rs.getInt("first_team_id"),
                        rs.getInt("second_team_id"),
                        rs.getDate("match_date"),
                        rs.getString("venue"),
                        rs.getString("result"),
                        rs.getString("status"),
                        rs.getInt("winner_team_id")
                ));
            }
        }
        return list;
    }
}