package com.m3bi.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiModel(description="All details of Sucessful Booking.")
@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int Id;
	
	@NotNull
	@ApiModelProperty(notes="User id of user booked Hotel")
	int userId;
	
	@NotNull
	@ApiModelProperty(notes="Hotel id of booked hotel")
	int hotelId;
	
	public Booking() {
		// TODO Auto-generated constructor stub
	}
	public Booking(int userId, int hotelId) {
		super();
		this.userId = userId;
		this.hotelId = hotelId;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	@Override
	public String toString() {
		return "Booking [Id=" + Id + ", userId=" + userId + ", hotelId=" + hotelId + "]";
	}
	
}
