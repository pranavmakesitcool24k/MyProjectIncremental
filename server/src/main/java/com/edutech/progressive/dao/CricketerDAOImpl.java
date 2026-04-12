package com.edutech.progressive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Cricketer;

public class CricketerDAOImpl implements CricketerDAO {

    @Override
    public int addCricketer(Cricketer cricketer) throws SQLException {
        String sql = "INSERT INTO cricketer (team_id, cricketer_name, age, nationality, experience, role, total_runs, total_wickets) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cricketer.getTeamId());
            ps.setString(2, cricketer.getCricketerName());
            ps.setInt(3, cricketer.getAge());
            ps.setString(4, cricketer.getNationality());
            ps.setInt(5, cricketer.getExperience());
            ps.setString(6, cricketer.getRole());
            ps.setInt(7, cricketer.getTotalRuns());
            ps.setInt(8, cricketer.getTotalWickets());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    @Override
    public Cricketer getCricketerById(int cricketerId) throws SQLException {
        String sql = "SELECT cricketer_id, team_id, cricketer_name, age, nationality, experience, role, total_runs, total_wickets FROM cricketer WHERE cricketer_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cricketerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cricketer(
                            rs.getInt("cricketer_id"),
                            rs.getInt("team_id"),
                            rs.getString("cricketer_name"),
                            rs.getInt("age"),
                            rs.getString("nationality"),
                            rs.getInt("experience"),
                            rs.getString("role"),
                            rs.getInt("total_runs"),
                            rs.getInt("total_wickets")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void updateCricketer(Cricketer cricketer) throws SQLException {
        String sql = "UPDATE cricketer SET team_id=?, cricketer_name=?, age=?, nationality=?, experience=?, role=?, total_runs=?, total_wickets=? WHERE cricketer_id=?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cricketer.getTeamId());
            ps.setString(2, cricketer.getCricketerName());
            ps.setInt(3, cricketer.getAge());
            ps.setString(4, cricketer.getNationality());
            ps.setInt(5, cricketer.getExperience());
            ps.setString(6, cricketer.getRole());
            ps.setInt(7, cricketer.getTotalRuns());
            ps.setInt(8, cricketer.getTotalWickets());
            ps.setInt(9, cricketer.getCricketerId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteCricketer(int cricketerId) throws SQLException {
        String sql = "DELETE FROM cricketer WHERE cricketer_id = ?";
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cricketerId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Cricketer> getAllCricketers() throws SQLException {
        String sql = "SELECT cricketer_id, team_id, cricketer_name, age, nationality, experience, role, total_runs, total_wickets FROM cricketer";
        List<Cricketer> list = new ArrayList<>();
        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Cricketer(
                        rs.getInt("cricketer_id"),
                        rs.getInt("team_id"),
                        rs.getString("cricketer_name"),
                        rs.getInt("age"),
                        rs.getString("nationality"),
                        rs.getInt("experience"),
                        rs.getString("role"),
                        rs.getInt("total_runs"),
                        rs.getInt("total_wickets")
                ));
            }
        }
        return list;
    }
}