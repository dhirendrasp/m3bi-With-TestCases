package com.m3bi.booking.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.m3bi.booking.entity.Bonus;
import com.m3bi.booking.entity.Booking;
import com.m3bi.booking.entity.Hotel;
import com.m3bi.booking.entity.PendingApproval;
import com.m3bi.booking.entity.User;
import com.m3bi.booking.exceptions.BonusNotFoundException;
import com.m3bi.booking.repository.BonusRepository;
import com.m3bi.booking.repository.PendingApprovalRepository;
import com.m3bi.booking.service.BookingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Bonus Resource", description="Shows Bonus related resources")
public class BonusController {

	@Autowired
	private BonusRepository bonusRepository; 
	
	@Autowired
	private BookingService bookingService; 
	
	@Autowired
	PendingApprovalRepository pendingApprovalRepository; 
	
	@ApiOperation(value="Get bouns by ID")
	@RequestMapping(value="/bonuses/{id}", method=RequestMethod.GET)
	public EntityModel<Bonus> getBonusById(@PathVariable int id) { 
		Optional<Bonus> bonus = bonusRepository.findById(id);
		if(!bonus.isPresent())
		{
			throw new BonusNotFoundException("Bonus for Id "+ id + " Not Found");
		}
		
		EntityModel<Bonus> resource = EntityModel.of(bonus.get());
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllBonusRecords());
		resource.add(link.withRel("all-bonuses"));
		
		return resource;
	}
	
	@ApiOperation(value="Get all Bouns records of Users")
	@GetMapping("/bonuses")
	public List<Bonus> getAllBonusRecords(){
		return bonusRepository.findAll();
	}
	
	/**
	 * method to update bonus
	 * @param bonus
	 * @param userId
	 * @return
	 */
	@ApiOperation(value="Update Bouns records of User and update status for booking which are in PENDINGAPPROVAL status.")
	@PutMapping("/bonuses/user/{userid}")
	public ResponseEntity<Object> updateBonus(@Valid @RequestBody Bonus bonus, @PathVariable("userid") int userId) {
		
		User user = bookingService.getuser(userId);
		//update user bonus
		Bonus updatedBonus = bookingService.updateBonus(new Bonus(bonus.getId(), bonus.getPoints()));
		
		//get current user
		Optional<PendingApproval> pendingApproval = pendingApprovalRepository.findByUserId(user.getId());
		
		//check if user booked hotel and it is in Pending approval stat 
		//check if after updating bonus. it is grater than od equal to the price of hotel
		if(pendingApproval.isPresent() && pendingApproval.get().getPrice() <= updatedBonus.getPoints()) {
				Hotel hotel = bookingService.getHotelStatus(pendingApproval.get().getHotelId());
				hotel.setStatus("BOOKED");
				bookingService.updateHotelStatus(hotel);
				Booking booking = bookingService.updateBooking(new Booking(user.getId(),hotel.getId()));
				bookingService.deletePreviousBooking(pendingApproval.get().getId());
		}
		
		URI location = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:9090/bonuses").path("/{id}").buildAndExpand(updatedBonus.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
}
