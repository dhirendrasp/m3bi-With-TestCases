package com.m3bi.booking.controller;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m3bi.booking.entity.Bonus;
import com.m3bi.booking.entity.Booking;
import com.m3bi.booking.entity.Hotel;
import com.m3bi.booking.entity.PendingApproval;
import com.m3bi.booking.entity.User;
import com.m3bi.booking.exceptions.UserNotFoundException;
import com.m3bi.booking.repository.BookingRepository;
import com.m3bi.booking.repository.PendingApprovalRepository;
import com.m3bi.booking.service.BookingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
	
@RestController
@Api(value="Booking Resource", description="Shows Booking related resources")
public class BookingController {

	@Autowired
	BookingService bookingService;
	
	@Autowired
	BookingRepository bookingRepository; 
	
	@Autowired
	PendingApprovalRepository pendingApprovalRepository; 

	@ApiOperation(value="Get specific booking detail.")
	@GetMapping("/bookings/{id}")
	public Booking getBookingById(@PathVariable int id){
		return bookingService.getBookingById(id);
	}
	
	@ApiOperation(value="Get All the booking details.")
	@GetMapping("/bookings")
	public List<Booking> getBookings(){
		return bookingService.getAllBooking();
	}
	
	/**
	 * method to keep checking the status of booking
	 * @param userId
	 * @return
	 */
	@ApiOperation(value="Get booking status details of specific user.")
	@GetMapping("/bookings/status/user/{userid}")
	public String getBookingStatusByUserId(@PathVariable("userid") int userId){
		
		Optional<Booking> booking = bookingRepository.findByUserId(userId);
		Optional<PendingApproval> pendingApproval = pendingApprovalRepository.findByUserId(userId);
		if (!(booking.isPresent() || pendingApproval.isPresent())) {
			throw new UserNotFoundException("Id "+userId+" Not found");
		}
		if(booking.isPresent()&& booking.get().getUserId()==userId)
			return "Your Hotel Is Booked. Booking ID: "+booking.get().getId()+" user id: "+userId;

		if(pendingApproval.isPresent() && pendingApproval.get().getStatus().equalsIgnoreCase("PENDINGAPPROVAL")){
			return "Your booking request is pending for approval";
		}
		return "Hotel Not Booked";
	}
	
	/**
	 * method to book hotel for specific user
	 * @param userId - accept user id
	 * @param hotelId - accept hotel id
	 * @return
	 */
	@ApiOperation(value="book hotel for specific user.")
	@PostMapping("/bookings/user/{userid}/hotel/{hotelid}")
	public ResponseEntity<String> bookHotel(@PathVariable("userid") int userId, @PathVariable("hotelid") int hotelId){
		
		//get status of selected hotel
		Hotel hotel = bookingService.getHotelStatus(hotelId);
		String status = hotel.getStatus();
		if(hotel.getStatus().equalsIgnoreCase("BOOKED"))
			return ResponseEntity.ok("Selected Hotel Is Not Avaliable For Booking.");
		
		//get selected user 
		User user = bookingService.getuser(userId);
		int price = bookingService.checkHotelPrice(hotel);
		
		//get bonus points of user
		Bonus bonus = bookingService.checkUserBonusPoints(user.getBonusId());
		int bonusPoints = bonus.getPoints();
		
		
		//New user trying to book Hotel 
		//compare the hotel price and bonus points and also check availability
		if((price <= bonusPoints) &&  status.equalsIgnoreCase("AVAILABLE")) {
			hotel.setStatus("BOOKED");
			
			//update hotel status to booked.
			bookingService.updateHotelStatus(hotel);
			
			//complete and add entry in booking table
			Booking booking = bookingService.updateBooking(new Booking(user.getId(),hotel.getId()));
			
			//URI location = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:9090/bookings").path("/{id}").buildAndExpand(booking.getId()).toUri();
			//return ResponseEntity.created(location).build();
			return ResponseEntity.ok("Your Hotel Is Booked. Booking ID: "+booking.getId()+" user id: "+user.getId());
		}
		//New User trying to book hotel with bonus point less than hotel price 
		else if((price > bonusPoints) &&  status.equalsIgnoreCase("AVAILABLE")){
			
			//Add booking entry in PendingApproval Table.
			bookingService.updatePendingApprovalTable(new PendingApproval(user.getId(),hotel.getId(),hotel.getPrice(), "PENDINGAPPROVAL"));
			hotel.setStatus("PENDINGAPPROVAL");
			
			//update hotel status to PENDINGAPPROVAL
			bookingService.updateHotelStatus(hotel);
			
			return ResponseEntity.ok("Hotel Pending For Approval");
		}
		//New user trying to book hotel with status PendingApproval
		else if((price <= bonusPoints) &&  status.equalsIgnoreCase("PENDINGAPPROVAL")) {
			
			//Get the pre-booked room details and user detail
			PendingApproval pendingApproval = bookingService.searchPreviousBookingByHotelId(hotel.getId());
			
			//delete previous booking on hotel  
			bookingService.deletePreviousBooking(pendingApproval.getId());
			hotel.setStatus("BOOKED");
			
			//update hotel status to booked.
			bookingService.updateHotelStatus(hotel);
			
			//complete the booking and add entry in booking table
			Booking booking = bookingService.updateBooking(new Booking(user.getId(), hotel.getId()));
			
			//URI location = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:9090/bookings").path("/{id}").buildAndExpand(booking.getId()).toUri();
			//return ResponseEntity.created(location).build(); 
			return ResponseEntity.ok("Your Hotel Is Booked. Booking ID: "+booking.getId()+" user id: "+user.getId());
		}
		//New User trying to book hotel with status PendingApproval and bonus point less than hotel price
		else {
			return ResponseEntity.badRequest().build();
		}
	}
}
