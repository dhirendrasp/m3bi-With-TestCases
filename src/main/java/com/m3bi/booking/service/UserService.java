package com.m3bi.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m3bi.booking.entity.User;
import com.m3bi.booking.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository; 
	public User saveUser(User user) {
		return userRepository.save(user);
	}

}
