package com.edutech.progressive.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.progressive.entity.Match;
import com.edutech.progressive.exception.NoMatchesFoundException;
import com.edutech.progressive.service.impl.MatchServiceImplJpa;

@RestController
@RequestMapping("/match")
public class MatchController {

    private MatchServiceImplJpa matchService;

    public MatchController(MatchServiceImplJpa matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        try {
            return new ResponseEntity<>(matchService.getAllMatches(), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Match> getMatchById(@PathVariable int matchId) {
        try {
            return new ResponseEntity<>(matchService.getMatchById(matchId), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Integer> addMatch(@RequestBody Match match) {
        try {
            return new ResponseEntity<>(matchService.addMatch(match), HttpStatus.CREATED);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{matchId}")
    public ResponseEntity<Void> updateMatch(@PathVariable int matchId, @RequestBody Match match) {
        try {
            match.setMatchId(matchId);
            matchService.updateMatch(match);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteMatch(@PathVariable int matchId) {
        try {
            matchService.deleteMatch(matchId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllMatchesByStatus(@PathVariable String status) {
        try {
            return new ResponseEntity<>(matchService.getAllMatchesByStatus(status), HttpStatus.OK);
        } catch (NoMatchesFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}