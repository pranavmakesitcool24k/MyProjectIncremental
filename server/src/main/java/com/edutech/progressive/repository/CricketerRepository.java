package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.progressive.entity.Cricketer;

public interface CricketerRepository extends JpaRepository<Cricketer, Integer> {
}