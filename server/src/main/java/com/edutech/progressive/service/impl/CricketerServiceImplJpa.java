package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Cricketer;
import com.edutech.progressive.exception.TeamCricketerLimitExceededException;
import com.edutech.progressive.repository.CricketerRepository;
import com.edutech.progressive.service.CricketerService;

@Service
public class CricketerServiceImplJpa implements CricketerService {

    private CricketerRepository cricketerRepository;

    public CricketerServiceImplJpa(CricketerRepository cricketerRepository) {
        this.cricketerRepository = cricketerRepository;
    }

    @Override
    public List<Cricketer> getAllCricketers() throws SQLException {
        return cricketerRepository.findAll();
    }

    @Override
    public Integer addCricketer(Cricketer cricketer) throws SQLException {
        int teamId = cricketer.getTeamId();
        if (teamId != 0) {
            long count = cricketerRepository.countByTeam_TeamId(teamId);
            if (count >= 11) {
                throw new TeamCricketerLimitExceededException("Team cricketer limit exceeded");
            }
        }
        return cricketerRepository.save(cricketer).getCricketerId();
    }

    @Override
    public List<Cricketer> getAllCricketersSortedByExperience() throws SQLException {
        List<Cricketer> list = cricketerRepository.findAll();
        list.sort(Comparator.comparingInt(Cricketer::getExperience));
        return list;
    }

    @Override
    public void updateCricketer(Cricketer cricketer) throws SQLException {
        int teamId = cricketer.getTeamId();
        if (teamId != 0) {
            Cricketer existing = cricketerRepository.findByCricketerId(cricketer.getCricketerId());
            long count = cricketerRepository.countByTeam_TeamId(teamId);
            if (count >= 11) {
                if (existing == null || existing.getTeamId() != teamId) {
                    throw new TeamCricketerLimitExceededException("Team cricketer limit exceeded");
                }
            }
        }
        cricketerRepository.save(cricketer);
    }

    @Override
    public void deleteCricketer(int cricketerId) throws SQLException {
        cricketerRepository.deleteById(cricketerId);
    }

    @Override
    public Cricketer getCricketerById(int cricketerId) throws SQLException {
        return cricketerRepository.findByCricketerId(cricketerId);
    }

    @Override
    public List<Cricketer> getCricketersByTeam(int teamId) throws SQLException {
        return cricketerRepository.findByTeam_TeamId(teamId);
    }
}