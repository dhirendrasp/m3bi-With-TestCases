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

import com.m3bi.booking.entity.Hotel;
import com.m3bi.booking.exceptions.BonusNotFoundException;
import com.m3bi.booking.repository.HotelRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Hotel Resource", description="Shows Hotel related resources")
public class HotelController {

	@Autowired
	private HotelRepository hotelRepository; 
	
	@ApiOperation(value="Get Hotel by hotel ID")
	@RequestMapping(value="/hotels/{id}", method=RequestMethod.GET)
	public EntityModel<Hotel> getBonusById(@PathVariable int id) { 
		Optional<Hotel> hotel = hotelRepository.findById(id);
		if(!hotel.isPresent())
		{
			throw new BonusNotFoundException("Bonus for Id "+ id + " Not Found");
		}
		
		EntityModel<Hotel> resource = EntityModel.of(hotel.get());
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllHotels());
		resource.add(link.withRel("all-bonuses"));
		
		return resource;
		
		//return user;
	}
	
	@ApiOperation(value="Get All Hotels")
	@GetMapping("/hotels")
	public List<Hotel> getAllHotels(){
		return hotelRepository.findAll();
	}
	
	@ApiOperation(value="Updating Hotel Avaliablity Status")
	@PutMapping("/hotels")
	public ResponseEntity<Object> updateBonus(@Valid @RequestBody Hotel hotel) {
		Hotel hotels = hotelRepository.save(hotel);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(hotels.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
}
