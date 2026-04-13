package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.progressive.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    Team findByTeamId(int teamId);
}