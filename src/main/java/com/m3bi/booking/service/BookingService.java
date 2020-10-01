package com.m3bi.booking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m3bi.booking.entity.Bonus;
import com.m3bi.booking.entity.Booking;
import com.m3bi.booking.entity.Hotel;
import com.m3bi.booking.entity.PendingApproval;
import com.m3bi.booking.entity.User;
import com.m3bi.booking.exceptions.BonusNotFoundException;
import com.m3bi.booking.exceptions.BookingNotFoundException;
import com.m3bi.booking.exceptions.HotelNotFoundException;
import com.m3bi.booking.exceptions.PendingApprovalNotFoundException;
import com.m3bi.booking.exceptions.UserNotFoundException;
import com.m3bi.booking.repository.BonusRepository;
import com.m3bi.booking.repository.BookingRepository;
import com.m3bi.booking.repository.HotelRepository;
import com.m3bi.booking.repository.PendingApprovalRepository;
import com.m3bi.booking.repository.UserRepository;

@Service
public class BookingService {

	@Autowired
	HotelRepository hotelRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BonusRepository bonusRepository;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	PendingApprovalRepository pendingApprovalRepository;
	
	public Hotel getHotelStatus(int hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		if(!hotel.isPresent())
			throw new HotelNotFoundException("Hotel Id "+hotelId+" not found.");
		return hotel.get();
	}

	public User getuser(int userId) {
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new UserNotFoundException("User Id "+ userId+ " not found");
		return user.get();
	}

	public int checkHotelPrice(Hotel hotel) {
		return hotel.getPrice();
	}

	public Bonus checkUserBonusPoints(int bonusId) {
		Optional<Bonus> bonus = bonusRepository.findById(bonusId);
		if(!bonus.isPresent())
			throw new BonusNotFoundException("Bonus Id "+bonusId+" not found");
		return bonus.get();
	}

	public Hotel updateHotelStatus(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

	public Booking updateBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	public PendingApproval updatePendingApprovalTable(PendingApproval pendingApproval) {
		 return pendingApprovalRepository.save(pendingApproval);
	}

	public PendingApproval searchPreviousBookingByHotelId(int id) {
		Optional<PendingApproval> pendingApproval = pendingApprovalRepository.findByHotelId(id);
		if(!pendingApproval.isPresent())
			throw new PendingApprovalNotFoundException("Pending Approval id "+id+" not found.");
		return pendingApproval.get();
	}

	public void deletePreviousBooking(int id) {
		pendingApprovalRepository.deleteById(id);
	}

	public Bonus updateBonus(Bonus bonus) {
		return bonusRepository.save(bonus);
	}

	public List<Booking> getAllBooking() {
		return bookingRepository.findAll();
	}

	public Booking getBookingById(int id) {
		Optional<Booking> booking = bookingRepository.findById(id);
		if(!booking.isPresent())
			throw new BookingNotFoundException("Booking Id "+ id+" not found");
		return booking.get();
	}

	public Booking getBookingByUserId(int userId) {
		Optional<Booking> booking = bookingRepository.findByUserId(userId);
		if(!booking.isPresent())
			throw new BookingNotFoundException("Booking Id "+ userId+" not found");
		return booking.get();
	}

	
}
