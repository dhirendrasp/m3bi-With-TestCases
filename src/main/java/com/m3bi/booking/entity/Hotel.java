package com.m3bi.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about Hotel.")
@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	@ApiModelProperty(notes="Current booking status of hotel.")
	String status;
	
	@ApiModelProperty(notes="Price of hotel.")
	int price;
	
	public Hotel() {
	}

	public Hotel(String status, int price) {
		super();
		this.status = status;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", status=" + status + ", price=" + price + "]";
	}
}
