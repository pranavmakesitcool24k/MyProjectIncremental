package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Team;
import com.edutech.progressive.exception.TeamAlreadyExistsException;
import com.edutech.progressive.exception.TeamDoesNotExistException;
import com.edutech.progressive.repository.CricketerRepository;
import com.edutech.progressive.repository.MatchRepository;
import com.edutech.progressive.repository.TeamRepository;
import com.edutech.progressive.service.TeamService;

@Service
public class TeamServiceImplJpa implements TeamService {

    private TeamRepository teamRepository;

    @Autowired(required = false)
    private CricketerRepository cricketerRepository;

    @Autowired(required = false)
    private MatchRepository matchRepository;

    public TeamServiceImplJpa(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> getAllTeams() throws SQLException {
        return teamRepository.findAll();
    }

    @Override
    public int addTeam(Team team) throws SQLException {
        Team existing = teamRepository.findByTeamName(team.getTeamName());
        if (existing != null) {
            throw new TeamAlreadyExistsException("Team already exists");
        }
        return teamRepository.save(team).getTeamId();
    }

    @Override
    public List<Team> getAllTeamsSortedByName() throws SQLException {
        List<Team> list = teamRepository.findAll();
        Collections.sort(list);
        return list;
    }

    @Override
    public Team getTeamById(int teamId) throws SQLException {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            throw new TeamDoesNotExistException("Team does not exist");
        }
        return team;
    }

    @Override
    public void updateTeam(Team team) throws SQLException {
        Team byId = teamRepository.findByTeamId(team.getTeamId());
        if (byId == null) {
            throw new TeamDoesNotExistException("Team does not exist");
        }

        Team sameName = teamRepository.findByTeamName(team.getTeamName());
        if (sameName != null && sameName.getTeamId() != team.getTeamId()) {
            throw new TeamAlreadyExistsException("Team already exists");
        }

        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(int teamId) throws SQLException {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            throw new TeamDoesNotExistException("Team does not exist");
        }

        if (matchRepository != null) {
            matchRepository.deleteByTeamId(teamId);
        }
        if (cricketerRepository != null) {
            cricketerRepository.deleteByTeam_TeamId(teamId);
        }
        teamRepository.deleteById(teamId);
    }
}