package com.techelevator.model;

import java.time.LocalDate;

public class Park {

    private String park_name;
    private Long park_id;
    private String location;
    private LocalDate date_established;
    private Long area;
    private Long annual_visitor_count;
    private String description;




    public String getParkName() {
        return park_name;
    }
    public void setParkName(String parkName) {
        this.park_name = parkName;
    }
    public Long getParkId() {
        return park_id;
    }
    public void setParkId(Long parkId) {
        this.park_id = parkId;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDate getDateEstablished() {
        return date_established;
    }
    public void setDateEstablished(LocalDate dateEstablished) {
        this.date_established = dateEstablished;
    }
    public Long getArea() {
        return area;
    }
    public void setArea(Long area) {
        this.area = area;
    }
    public Long getAnnualVisCount() {
        return annual_visitor_count;
    }
    public void setAnnualVisCount(Long annualVisCount) {
        this.annual_visitor_count = annualVisCount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
