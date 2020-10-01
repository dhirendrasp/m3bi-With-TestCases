package com.m3bi.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about user.")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	@ApiModelProperty(notes="Name should have atleast 2 character")
	@Size(min=3, message="Name Should have atleast 3 character")
	String name;
	
	@ApiModelProperty(notes="Bonus Point of User")
	@Min(value=0)
	int bonusId;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String name, int bonusId) {
		super();
		this.name = name;
		this.bonusId = bonusId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBonusId() {
		return bonusId;
	}

	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", bonusId=" + bonusId + "]";
	}

}
