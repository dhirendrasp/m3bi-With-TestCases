package com.m3bi.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details of pending for approval bookings.")
@Entity
public class PendingApproval {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	@NotNull
	@ApiModelProperty(notes="User Id of user pending for approval")
	int userId;
	
	@NotNull
	@ApiModelProperty(notes="Hotel id of the hotel pending for approval")
	int hotelId;
	
	@NotNull
	@ApiModelProperty(notes="Price of the hotel pending for approval")
	@Min(value=0)
	int price;
	
	@NotNull
	@ApiModelProperty(notes="Booking Status")
	String status;
	
	public PendingApproval() {
		// TODO Auto-generated constructor stub
	}

	public PendingApproval(int userId, int hotelId, int price, String status) {
		super();
		this.userId = userId;
		this.hotelId = hotelId;
		this.price = price;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PendingApproval [id=" + id + ", userId=" + userId + ", hotelId=" + hotelId + ", price=" + price
				+ ", status=" + status + "]";
	}
}
