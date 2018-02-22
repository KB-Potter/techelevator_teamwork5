package com.techelevator.model.jdbc;

import com.techelevator.model.Park;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;


public class JDBCParkDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCParkDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public List<Park> getParkInfo(long choice){
        List<Park> parkInfo = new ArrayList<>();
        String park = ("SELECT * FROM park WHERE park_id = ? ");
        jdbcTemplate.update(park, choice);
        SqlRowSet parkNextRow = jdbcTemplate.queryForRowSet(park);
        while(parkNextRow.next()) {
            parkInfo.add(mapRowToPark(parkNextRow));
        }
        return parkInfo;
    }

    public List<Park> getAllParks(){
        List<Park> parkInfo = new ArrayList<>();
        String parks = "SELECT * FROM park";
        SqlRowSet parkNextRow = jdbcTemplate.queryForRowSet(parks);
        while(parkNextRow.next()) {
            parkInfo.add(mapRowToPark(parkNextRow));
        }
        return parkInfo;

    }
    private Park mapRowToPark(SqlRowSet parkNextRow) {
        Park thePark = new Park();
        thePark.setParkId(parkNextRow.getLong("park_id"));
        thePark.setParkName(parkNextRow.getString("name"));
        thePark.setLocation(parkNextRow.getString("location"));
        thePark.setDateEstablished(parkNextRow.getDate("established_date").toLocalDate());
        thePark.setArea(parkNextRow.getLong("area"));
        thePark.setAnnualVisCount(parkNextRow.getLong("annual_visitor_count"));
        thePark.setDescription(parkNextRow.getString("description"));

        return thePark;
    }

    public List<Park> getAllCampgroundsByParkId(){
        return null;

    }
}