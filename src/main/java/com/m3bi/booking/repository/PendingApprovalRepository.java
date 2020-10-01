package com.m3bi.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.m3bi.booking.entity.PendingApproval;
import com.m3bi.booking.entity.User;

@Repository
public interface PendingApprovalRepository extends JpaRepository<PendingApproval, Integer>{

	Optional<PendingApproval> findByHotelId(int id);

	Optional<PendingApproval> findByUserId(int id);
}
