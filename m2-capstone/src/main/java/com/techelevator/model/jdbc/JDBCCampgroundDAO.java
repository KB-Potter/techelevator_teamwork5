package com.techelevator.model.jdbc;

import com.techelevator.model.Campground;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {

    private final JdbcTemplate jdbcTemplate;

    public JDBCCampgroundDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Campground> getAvailableCampgrounds(Park park) {
        List<Campground> listOfCampgrounds = new ArrayList<>();
        Long parkId = park.getParkId();

        String sqlAvailableCampgrounds = "SELECT * FROM campground "
                + "WHERE park_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAvailableCampgrounds, parkId);

        while(results.next()) {
            Campground campground = mapRowToCampground(results);
            listOfCampgrounds.add(campground);
        }

        return listOfCampgrounds;
    }

    private Campground mapRowToCampground(SqlRowSet results) {
        Campground campground;
        campground = new Campground();
        campground.setCampgroundId(results.getInt("campground_id"));
        campground.setParkId(results.getInt("park_id"));
        campground.setName(results.getString("name"));
        campground.setOpeningDate(results.getString("open_from_mm"));
        campground.setClosingDate(results.getString("open_to_mm"));
        campground.setDailyFee(results.getBigDecimal("daily_fee"));
        return campground;
    }

}