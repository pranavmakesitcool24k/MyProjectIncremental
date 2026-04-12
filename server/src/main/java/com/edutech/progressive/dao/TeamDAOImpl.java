package com.edutech.progressive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Team;

public class TeamDAOImpl implements TeamDAO {

    @Override
    public int addTeam(Team team) throws SQLException {
        try (Connection con = DatabaseConnectionManager.getConnection();
             Statement st = con.createStatement()) {

            st.execute("SET SESSION sql_mode = CONCAT(@@SESSION.sql_mode, ',NO_AUTO_VALUE_ON_ZERO')");

            String sql = "INSERT INTO team (team_id, team_name, location, owner_name, establishment_year) " +
                         "VALUES (?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE " +
                         "team_name = VALUES(team_name), " +
                         "location = VALUES(location), " +
                         "owner_name = VALUES(owner_name), " +
                         "establishment_year = VALUES(establishment_year)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, team.getTeamId());
                ps.setString(2, team.getTeamName());
                ps.setString(3, team.getLocation());
                ps.setString(4, team.getOwnerName());
                ps.setInt(5, team.getEstablishmentYear());
                ps.executeUpdate();
            }

            return 0;
        }
    }

    @Override
    public Team getTeamById(int teamId) throws SQLException {
        String sql = "SELECT team_id, team_name, location, owner_name, establishment_year FROM team WHERE team_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Team(
                            rs.getInt("team_id"),
                            rs.getString("team_name"),
                            rs.getString("location"),
                            rs.getString("owner_name"),
                            rs.getInt("establishment_year")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void updateTeam(Team team) throws SQLException {
        String sql = "UPDATE team SET team_name=?, location=?, owner_name=?, establishment_year=? WHERE team_id=?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, team.getTeamName());
            ps.setString(2, team.getLocation());
            ps.setString(3, team.getOwnerName());
            ps.setInt(4, team.getEstablishmentYear());
            ps.setInt(5, team.getTeamId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteTeam(int teamId) throws SQLException {
        String sql = "DELETE FROM team WHERE team_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Team> getAllTeams() throws SQLException {
        String sql = "SELECT team_id, team_name, location, owner_name, establishment_year FROM team";
        List<Team> list = new ArrayList<>();
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Team(
                        rs.getInt("team_id"),
                        rs.getString("team_name"),
                        rs.getString("location"),
                        rs.getString("owner_name"),
                        rs.getInt("establishment_year")
                ));
            }
        }
        return list;
    }
}
