package com.techelevator.model;

import java.util.List;

public interface ReservationDAO {
	
	public List<Reservation> getAllReservations(); //need to display names when we do this
	public List<Reservation> searchReservationsByName(String nameSearch); //don't know if we want this? 
	public Reservation bookReservation(String reservationName);
	//basically doing an add name to list here
	
}
