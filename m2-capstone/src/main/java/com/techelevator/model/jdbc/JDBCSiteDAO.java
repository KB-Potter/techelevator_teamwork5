package com.techelevator.model.jdbc;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

    private final JdbcTemplate jdbcTemplate;

    public JDBCSiteDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Site> getAvailableSites(Campground campground, LocalDate arrivalDate, LocalDate departureDate){
        List<Site> availableSites = new ArrayList<>();


        String sqlAvailableSites = "SELECT DISTINCT * FROM site WHERE campground_id = ? AND site_id NOT IN (SELECT site.site_id  "
                + " FROM reservation "
                + " JOIN site ON site.site_id = reservation.site_id "
                + " JOIN campground ON campground.campground_id = site.campground_id "
                + " WHERE site.campground_id = ? "
                + " AND ((?  BETWEEN reservation.from_date AND reservation.to_date)  "
                + " OR (?  BETWEEN reservation.from_date AND reservation.to_date) "
                + " OR (reservation.from_date BETWEEN ? AND ?) "
                + " OR (reservation.to_date BETWEEN ? AND ?))) "
                + " LIMIT 5; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAvailableSites, campground.getCampgroundId(), campground.getCampgroundId(),
                arrivalDate, departureDate, arrivalDate, departureDate,arrivalDate, departureDate) ;
        while(results.next()) {
            Site site = mapRowToSite(results);
            availableSites.add(site);
        }

        return availableSites;
    }

    public List<Site> getAvailableSitesAdvanced(Campground campground, LocalDate arrivalDate, LocalDate departureDate){
        List<Site> availableSites = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        System.out.println("How many people will be staying? (press 0 if you do not care.)");
        int maxOccupancy = in.nextInt();
        System.out.println("What size RV do you have? (press 0 if you are not bringing an RV)");
        int maxRvLength = in.nextInt();
        System.out.println("Do you need wheelchair access? (Y/N)");
        boolean accessible = in.nextBoolean();
        System.out.println("Do you need utilities access? (Y/N)");
        boolean utilities = in.nextBoolean();



        String sqlAvailableSites = "SELECT DISTINCT * FROM site "
                + " WHERE campground_id = ? AND site.max_occupancy >= ? AND site.max_rv_length >= ? AND (site.utilities = true OR site.utilities = ?) AND (site.accessible = true OR site.accessible = ?) AND site_id NOT IN (SELECT site.site_id  "
                + " FROM reservation "
                + " JOIN site ON site.site_id = reservation.site_id "
                + " JOIN campground ON campground.campground_id = site.campground_id "
                + " WHERE site.campground_id = ? "
                + " AND ((?  BETWEEN reservation.from_date AND reservation.to_date)  "
                + " OR (?  BETWEEN reservation.from_date AND reservation.to_date) "
                + " OR (reservation.from_date BETWEEN ? AND ?) "
                + " OR (reservation.to_date BETWEEN ? AND ?))) "
                + " LIMIT 5; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAvailableSites, campground.getCampgroundId(), maxOccupancy, maxRvLength, utilities, accessible, campground.getCampgroundId(),
                arrivalDate, departureDate, arrivalDate, departureDate,arrivalDate, departureDate) ;
        while(results.next()) {
            Site site = mapRowToSite(results);
            availableSites.add(site);
        }

        return availableSites;
    }

    private Site mapRowToSite(SqlRowSet results) {
        Site site;
        site = new Site();
        site.setSiteId(results.getLong("site_id"));
        site.setCampgroundId(results.getLong("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }
}
