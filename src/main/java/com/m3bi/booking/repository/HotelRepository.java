package com.m3bi.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.m3bi.booking.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>{

}
