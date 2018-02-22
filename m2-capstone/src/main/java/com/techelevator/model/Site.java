package com.techelevator.model;

public class Site {
    private long siteId;
    private long campgroundId;
    private Integer siteNumber;
    private Integer maxOccupancy;
    private boolean accessible;
    private Integer maxRVLength;
    private boolean utilities;

    public long getSiteId() {
        return siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public long getCampgroundId() {
        return campgroundId;
    }

    public void setCampgroundId(long campgroundId) {
        this.campgroundId = campgroundId;
    }

    public Integer getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(Integer siteNumber) {
        this.siteNumber = siteNumber;
    }

    public Integer getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(Integer maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public Integer getMaxRVLength() {
        return maxRVLength;
    }

    public void setMaxRVLength(Integer maxRVLength) {
        this.maxRVLength = maxRVLength;
    }

    public boolean isUtilities() {
        return utilities;
    }

    public void setUtilities(boolean utilities) {
        this.utilities = utilities;
    }
}
