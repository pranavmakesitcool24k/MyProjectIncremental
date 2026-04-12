package com.edutech.progressive.dao;

import java.sql.SQLException;
import java.util.List;

import com.edutech.progressive.entity.Cricketer;

public interface CricketerDAO {

    int addCricketer(Cricketer cricketer) throws SQLException;

    Cricketer getCricketerById(int cricketerId) throws SQLException;

    void updateCricketer(Cricketer cricketer) throws SQLException;

    void deleteCricketer(int cricketerId) throws SQLException;

    List<Cricketer> getAllCricketers() throws SQLException;
}