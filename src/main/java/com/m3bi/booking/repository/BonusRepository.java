package com.m3bi.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.m3bi.booking.entity.Bonus;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Integer>{

}
