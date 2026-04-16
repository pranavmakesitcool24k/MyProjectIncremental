package com.edutech.progressive.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.progressive.entity.Team;
import com.edutech.progressive.exception.TeamAlreadyExistsException;
import com.edutech.progressive.exception.TeamDoesNotExistException;
import com.edutech.progressive.service.TeamService;
import com.edutech.progressive.service.impl.TeamServiceImplArraylist;
import com.edutech.progressive.service.impl.TeamServiceImplJpa;

@RestController
@RequestMapping("/team")
public class TeamController {

    private TeamService arrayListService = new TeamServiceImplArraylist();
    private TeamService jpaService;

    public TeamController(TeamServiceImplJpa jpaService) {
        this.jpaService = jpaService;
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        try {
            return new ResponseEntity<>(jpaService.getAllTeams(), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<?> getTeamById(@PathVariable int teamId) {
        try {
            return new ResponseEntity<>(jpaService.getTeamById(teamId), HttpStatus.OK);
        } catch (TeamDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addTeam(@RequestBody Team team) {
        try {
            int id = jpaService.addTeam(team);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (TeamAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<?> updateTeam(@PathVariable int teamId, @RequestBody Team team) {
        try {
            team.setTeamId(teamId);
            jpaService.updateTeam(team);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TeamAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TeamDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int teamId) {
        try {
            jpaService.deleteTeam(teamId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Team>> getAllTeamsFromArrayList() throws SQLException {
        return new ResponseEntity<>(arrayListService.getAllTeams(), HttpStatus.OK);
    }

    @PostMapping("/toArrayList")
    public ResponseEntity<Integer> addTeamToArrayList(@RequestBody Team team) throws SQLException {
        return new ResponseEntity<>(arrayListService.addTeam(team), HttpStatus.CREATED);
    }

    @GetMapping("/fromArrayList/sorted")
    public ResponseEntity<List<Team>> getAllTeamsSortedByNameFromArrayList() throws SQLException {
        return new ResponseEntity<>(arrayListService.getAllTeamsSortedByName(), HttpStatus.OK);
    }
}
