package com.techelevator.model;

import java.util.List;

    public interface ParkDAO {
        public List<Park> getAllParks();
        public List<Park> getParkInfo(long choice);
        public List<Park> getAllCampgroundsByParkId();
    }

