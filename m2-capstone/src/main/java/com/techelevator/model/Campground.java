package com.techelevator.model;

public class Campground {
	
	private int campground_id; 
	private int park_id;							//need all of the variables in the tables from the database table
	private String name; 			//getters and setters for both so we can call them in our menu/calls from table
	private int open_from_mm; 		//this is the plain object, java object, we put all of the methods in here
	private int open_to_mm; 			//the dao is where an abstract interface is made so we can refer to objects generically 
	private double money; 			//the jdbc basically only exists to connect the sql stuff to java
	
	public int getCampground_id() {
		return campground_id;
	}
	public void setCampground_id(int campground_id) {
		this.campground_id = campground_id;
	}
	public int getPark_id() {
		return park_id;
	}
	
	public void setPark_id(int park_id) {
		this.park_id = park_id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public int getOpen_from_mm() {
		return open_from_mm;
	}
	public void setOpen_from_mm(int open_from_mm) {
		this.open_from_mm = open_from_mm;
	}
	public int getOpen_to_mm() {
		return open_to_mm;
	}
	public void setOpen_to_mm(int open_to_mm) {
		this.open_to_mm = open_to_mm;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
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
