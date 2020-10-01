package com.m3bi.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about User Bonus.")
@Entity
public class Bonus {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	@ApiModelProperty(notes="Bonus Points")
	int points;

	public Bonus() {
		// TODO Auto-generated constructor stub
	}


	public Bonus(int id, int points) {
		super();
		this.id = id;
		this.points = points;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	@Override
	public String toString() {
		return "Bonus [id=" + id + ", points=" + points + "]";
	}
	
}
