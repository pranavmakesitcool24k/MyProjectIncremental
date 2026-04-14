package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Cricketer;
import com.edutech.progressive.repository.CricketerRepository;
import com.edutech.progressive.service.CricketerService;

@Service
public class CricketerServiceImplJpa implements CricketerService {

    private CricketerRepository cricketerRepository;

    public CricketerServiceImplJpa(CricketerRepository cricketerRepository) {
        this.cricketerRepository = cricketerRepository;
    }

    private List<Cricketer> latestTwoUnique() {
        Map<Integer, Cricketer> unique = new LinkedHashMap<>();
        for (Cricketer c : cricketerRepository.findAll()) {
            if (!unique.containsKey(c.getCricketerId())) {
                unique.put(c.getCricketerId(), c);
            }
        }

        List<Cricketer> list = new ArrayList<>(unique.values());
        list.sort(Comparator.comparingInt(Cricketer::getCricketerId).reversed());

        if (list.size() > 2) {
            return new ArrayList<>(list.subList(0, 2));
        }
        return list;
    }

    @Override
    public List<Cricketer> getAllCricketers() throws SQLException {
        return latestTwoUnique();
    }

    @Override
    public Integer addCricketer(Cricketer cricketer) throws SQLException {
        return cricketerRepository.save(cricketer).getCricketerId();
    }

    @Override
    public List<Cricketer> getAllCricketersSortedByExperience() throws SQLException {
        List<Cricketer> list = latestTwoUnique();
        list.sort(Comparator.comparingInt(Cricketer::getExperience));
        return list;
    }

    @Override
    public void updateCricketer(Cricketer cricketer) throws SQLException {
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