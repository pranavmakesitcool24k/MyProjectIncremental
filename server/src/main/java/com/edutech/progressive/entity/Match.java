package com.edutech.progressive.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matchId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"cricketers", "firstTeamMatches", "secondTeamMatches", "winnerTeamMatches"})
    private Team firstTeam;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"cricketers", "firstTeamMatches", "secondTeamMatches", "winnerTeamMatches"})
    private Team secondTeam;

    @Temporal(TemporalType.DATE)
    private Date matchDate;

    private String venue;
    private String result;
    private String status;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"cricketers", "firstTeamMatches", "secondTeamMatches", "winnerTeamMatches"})
    private Team winnerTeam;

    public Match() {
    }

    public Match(int matchId, int firstTeamId, int secondTeamId, Date matchDate, String venue, String result, String status, int winnerTeamId) {
        this.matchId = matchId;
        this.firstTeam = new Team(firstTeamId);
        this.secondTeam = new Team(secondTeamId);
        this.matchDate = matchDate;
        this.venue = venue;
        this.result = result;
        this.status = status;
        this.winnerTeam = new Team(winnerTeamId);
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getFirstTeamId() {
        return firstTeam == null ? 0 : firstTeam.getTeamId();
    }

    public void setFirstTeamId(int firstTeamId) {
        if (this.firstTeam == null) this.firstTeam = new Team();
        this.firstTeam.setTeamId(firstTeamId);
    }

    public int getSecondTeamId() {
        return secondTeam == null ? 0 : secondTeam.getTeamId();
    }

    public void setSecondTeamId(int secondTeamId) {
        if (this.secondTeam == null) this.secondTeam = new Team();
        this.secondTeam.setTeamId(secondTeamId);
    }

    public int getWinnerTeamId() {
        return winnerTeam == null ? 0 : winnerTeam.getTeamId();
    }

    public void setWinnerTeamId(int winnerTeamId) {
        if (this.winnerTeam == null) this.winnerTeam = new Team();
        this.winnerTeam.setTeamId(winnerTeamId);
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }
}