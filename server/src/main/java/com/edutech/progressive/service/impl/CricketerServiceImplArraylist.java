package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.edutech.progressive.entity.Cricketer;
import com.edutech.progressive.service.CricketerService;

public class CricketerServiceImplArraylist implements CricketerService {

    private static List<Cricketer> cricketerList = new ArrayList<>();

    @Override
    public List<Cricketer> getAllCricketers() {
        return cricketerList;
    }

    @Override
    public Integer addCricketer(Cricketer cricketer) {
        cricketerList.add(cricketer);
        return cricketerList.size();
    }

    @Override
    public List<Cricketer> getAllCricketersSortedByExperience() {
        List<Cricketer> sortedList = new ArrayList<>(cricketerList);
        Collections.sort(sortedList);
        return sortedList;
    }

    @Override
    public void emptyArrayList() {
        cricketerList = new ArrayList<>();
    }
}