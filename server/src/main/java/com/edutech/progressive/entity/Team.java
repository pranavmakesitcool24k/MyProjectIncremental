package com.edutech.progressive.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Team implements Comparable<Team> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamId;

    private String teamName;
    private String location;
    private String ownerName;
    private int establishmentYear;

    @OneToMany(mappedBy = "team")
    @JsonIgnoreProperties({"team"})
    private List<Cricketer> cricketers = new ArrayList<>();

    @OneToMany(mappedBy = "firstTeam")
    @JsonIgnoreProperties({"firstTeam", "secondTeam", "winnerTeam"})
    private List<Match> firstTeamMatches = new ArrayList<>();

    @OneToMany(mappedBy = "secondTeam")
    @JsonIgnoreProperties({"firstTeam", "secondTeam", "winnerTeam"})
    private List<Match> secondTeamMatches = new ArrayList<>();

    @OneToMany(mappedBy = "winnerTeam")
    @JsonIgnoreProperties({"firstTeam", "secondTeam", "winnerTeam"})
    private List<Match> winnerTeamMatches = new ArrayList<>();

    public Team() {
    }

    public Team(int teamId) {
        this.teamId = teamId;
    }

    public Team(int teamId, String teamName, String location, String ownerName, int establishmentYear) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.location = location;
        this.ownerName = ownerName;
        this.establishmentYear = establishmentYear;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getEstablishmentYear() {
        return establishmentYear;
    }

    public void setEstablishmentYear(int establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public List<Cricketer> getCricketers() {
        return cricketers;
    }

    public void setCricketers(List<Cricketer> cricketers) {
        this.cricketers = cricketers;
    }

    public List<Match> getFirstTeamMatches() {
        return firstTeamMatches;
    }

    public void setFirstTeamMatches(List<Match> firstTeamMatches) {
        this.firstTeamMatches = firstTeamMatches;
    }

    public List<Match> getSecondTeamMatches() {
        return secondTeamMatches;
    }

    public void setSecondTeamMatches(List<Match> secondTeamMatches) {
        this.secondTeamMatches = secondTeamMatches;
    }

    public List<Match> getWinnerTeamMatches() {
        return winnerTeamMatches;
    }

    public void setWinnerTeamMatches(List<Match> winnerTeamMatches) {
        this.winnerTeamMatches = winnerTeamMatches;
    }

    @Override
    public int compareTo(Team other) {
        if (this.teamName == null && other.teamName == null) return 0;
        if (this.teamName == null) return -1;
        if (other.teamName == null) return 1;
        return this.teamName.compareToIgnoreCase(other.teamName);
    }
}
