package com.edutech.progressive.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Vote;
import com.edutech.progressive.repository.VoteRepository;

@Service
public class VoteServiceImpl {

    private VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> list = voteRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<Integer> createVote(Vote vote) {
        int id = voteRepository.save(vote).getVoteId();
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    public ResponseEntity<Map<String, Long>> getVotesCountOfAllCategories() {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("Team", safeCount("Team"));
        map.put("Batsman", safeCount("Batsman"));
        map.put("Bowler", safeCount("Bowler"));
        map.put("All-rounder", safeCount("All-rounder"));
        map.put("Wicketkeeper", safeCount("Wicketkeeper"));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private long safeCount(String category) {
        Long c = voteRepository.countByCategory(category);
        return c == null ? 0L : c;
    }
}