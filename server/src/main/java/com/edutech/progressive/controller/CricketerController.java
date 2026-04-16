package com.edutech.progressive.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.progressive.entity.Cricketer;
import com.edutech.progressive.exception.TeamCricketerLimitExceededException;
import com.edutech.progressive.service.impl.CricketerServiceImplJpa;

@RestController
@RequestMapping("/cricketer")
public class CricketerController {

    private CricketerServiceImplJpa cricketerService;

    public CricketerController(CricketerServiceImplJpa cricketerService) {
        this.cricketerService = cricketerService;
    }

    @GetMapping
    public ResponseEntity<List<Cricketer>> getAllCricketers() {
        try {
            return new ResponseEntity<>(cricketerService.getAllCricketers(), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cricketerId}")
    public ResponseEntity<Cricketer> getCricketerById(@PathVariable int cricketerId) {
        try {
            return new ResponseEntity<>(cricketerService.getCricketerById(cricketerId), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCricketer(@RequestBody Cricketer cricketer) {
        try {
            return new ResponseEntity<>(cricketerService.addCricketer(cricketer), HttpStatus.CREATED);
        } catch (TeamCricketerLimitExceededException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{cricketerId}")
    public ResponseEntity<Void> updateCricketer(@PathVariable int cricketerId, @RequestBody Cricketer cricketer) {
        try {
            cricketer.setCricketerId(cricketerId);
            cricketerService.updateCricketer(cricketer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cricketerId}")
    public ResponseEntity<Void> deleteCricketer(@PathVariable int cricketerId) {
        try {
            cricketerService.deleteCricketer(cricketerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cricketer/team/{teamId}")
    public ResponseEntity<List<Cricketer>> getCricketersByTeam(@PathVariable int teamId) {
        try {
            return new ResponseEntity<>(cricketerService.getCricketersByTeam(teamId), HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}