package com.edutech.progressive.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.progressive.entity.Team;
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

    // Day 5
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

    // Day 6
    @PostMapping
    public ResponseEntity<Integer> addTeam(@RequestBody Team team) throws SQLException {
        return new ResponseEntity<>(jpaService.addTeam(team), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTeam(@PathVariable int id, @RequestBody Team team) throws SQLException {
        team.setTeamId(id);
        jpaService.updateTeam(team);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int id) throws SQLException {
        jpaService.deleteTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}