package com.edutech.progressive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.progressive.entity.Cricketer;

public interface CricketerRepository extends JpaRepository<Cricketer, Integer> {

    Cricketer findByCricketerId(int cricketerId);

    List<Cricketer> findByTeam_TeamId(int teamId);

    @Modifying
    @Transactional
    void deleteByTeam_TeamId(int teamId);
}
