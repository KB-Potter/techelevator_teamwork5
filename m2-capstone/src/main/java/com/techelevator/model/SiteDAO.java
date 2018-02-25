package com.techelevator.model;



import java.util.List;
import java.time.LocalDate;
import com.techelevator.model.Site;

public interface SiteDAO {
    public List<Site> getAvailableSites(Campground campground, LocalDate fromDate, LocalDate toDate);

    public List<Site> getAvailableSitesAdvanced(Campground campground, LocalDate fromDate, LocalDate toDate);
}

