package com.m3bi.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.m3bi.booking.entity.Booking;

@Repository	
public interface BookingRepository extends JpaRepository<Booking, Integer>{

	Optional<Booking> findByUserId(int id);
	Optional<Booking> findByHotelId(int id);
}
