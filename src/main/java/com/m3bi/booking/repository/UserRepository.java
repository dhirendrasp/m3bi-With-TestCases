package com.m3bi.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.m3bi.booking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByName(String name);
}
