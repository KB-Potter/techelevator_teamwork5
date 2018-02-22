package com.techelevator.model;

import java.math.BigDecimal;

public class Campground {
	
	private long campgroundId;
	private long parkId;							//need all of the variables in the tables from the database table
	private String name; 			//getters and setters for both so we can call them in our menu/calls from table
	private String openingDate; 		//this is the plain object, java object, we put all of the methods in here
	private String closingDate; 			//the dao is where an abstract interface is made so we can refer to objects generically
	private BigDecimal money; 			//the jdbc basically only exists to connect the sql stuff to java

	public long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}

	public long getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}

/* when we first open the application, we want a main menu, with options for seeing the parks, seeing the campgrounds, booking a site, and exits
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
