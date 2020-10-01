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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.m3bi.booking.entity.Bonus;
import com.m3bi.booking.entity.User;
import com.m3bi.booking.exceptions.BonusNotFoundException;
import com.m3bi.booking.exceptions.UserNotFoundException;
import com.m3bi.booking.repository.BonusRepository;
import com.m3bi.booking.repository.UserRepository;
import com.m3bi.booking.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="User Resource", description="Shows User related resources")
public class UserController {

	@Autowired
	private BonusRepository bonusRepository; 
	
	@Autowired
	private UserRepository userRepository; 
	
	@ApiOperation(value="Get User by ID")
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public EntityModel<User> getSelectedUser(@PathVariable int id) { 
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("Id "+id+" Not found");
		}
		
		EntityModel<User> resource = EntityModel.of(user.get());
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllUsers());
		resource.add(link.withRel("all-users"));
		
		return resource;
		
		//return user;
	}
	
	@ApiOperation(value="Get User by Name")
	@RequestMapping(value="/usersbyname/{name}", method=RequestMethod.GET)
	public EntityModel<User> getSelectedUserByName(@PathVariable String name) { 
		Optional<User> user = userRepository.findByName(name);
		if (user == null) {
			throw new UserNotFoundException("Name "+name+" Not found");
		}
		Optional<Bonus> bonus = bonusRepository.findById(user.get().getBonusId());
		if(!bonus.isPresent())
		{
			throw new BonusNotFoundException("Bonus for User Id "+ user.get().getBonusId() + " Not Found");
		}
		EntityModel<User> resource = EntityModel.of(user.get());
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BonusController.class).getBonusById(user.get().getBonusId()));
		resource.add(link.withRel("all-users"));
		
		return resource;
	}
	
	@ApiOperation(value="Get details of all User")
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<Void> addUser(@RequestBody User user) {
		User usersaved = userService.saveUser(user);
		
		URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usersaved.getId()).toUri();
		
		return ResponseEntity.created(uriLocation).build();
		
		
	}
}