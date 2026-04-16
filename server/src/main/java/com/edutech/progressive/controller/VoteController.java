package com.edutech.progressive.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.progressive.entity.Vote;
import com.edutech.progressive.service.impl.VoteServiceImpl;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private VoteServiceImpl voteService;

    public VoteController(VoteServiceImpl voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        try {
            return voteService.getAllVotes();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Integer> createVote(@RequestBody Vote vote) {
        try {
            return voteService.createVote(vote);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getVotesCountOfAllCategories() {
        try {
            return voteService.getVotesCountOfAllCategories();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}