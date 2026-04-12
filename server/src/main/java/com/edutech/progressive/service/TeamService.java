package com.edutech.progressive.service;

import java.sql.SQLException;
import java.util.List;

import com.edutech.progressive.entity.Team;

public interface TeamService {

    List<Team> getAllTeams() throws SQLException;

    int addTeam(Team team) throws SQLException;

    List<Team> getAllTeamsSortedByName() throws SQLException;

    default void emptyArrayList() {
    }

    default Team getTeamById(int teamId) throws SQLException {
        return null;
    }

    default void updateTeam(Team team) throws SQLException {
    }

    default void deleteTeam(int teamId) throws SQLException {
    }
}