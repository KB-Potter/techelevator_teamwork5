package com.techelevator.model;
import java.time.LocalDate;
import java.util.List;




public interface ReservationDAO {
    public List<Reservation> getAllReservations(long campId, LocalDate fromDate,LocalDate toDate);
    public void setReservation(long siteId, LocalDate fromDate,LocalDate toDate, String name);
    public List<Reservation> getConfirmId(String name,LocalDate fromDate);
}