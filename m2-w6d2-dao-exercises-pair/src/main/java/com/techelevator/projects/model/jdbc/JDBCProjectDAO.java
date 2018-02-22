package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Project> getAllActiveProjects() {
		ArrayList<Project> projects = new ArrayList<>();
		Project theProject = null;
		String sqlgetAllActiveProjects = " SELECT project_id, name, from_date, to_date " +
				" FROM project " +
				" WHERE from_date IS NOT NULL AND to_Date IS NOT NULL ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetAllActiveProjects);

		while(results.next()) {
			theProject = mapRowToProject(results);
			projects.add(theProject);
		}
		return projects;

	}



	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) { //Does not actually remove employee but works in sql
		String sqlremoveEmployeeFromProject = " DELETE FROM project_employee " +
				" WHERE project_id = ? AND employee_id = ?";
		jdbcTemplate.update(sqlremoveEmployeeFromProject, projectId, employeeId);
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) { //Does not actually add but works in sql
		String sqladdEmployeeToProject = "UPDATE project_employee " +  "SET project_id = ? " +
				" WHERE employee_id = ? ";
		jdbcTemplate.update(sqladdEmployeeToProject, projectId, employeeId);

	}


	private Project mapRowToProject(SqlRowSet results) {
		Project theProject;
		theProject = new Project();
		theProject.setId(results.getLong("project_id"));
		theProject.setName(results.getString("name"));
		theProject.setStartDate(results.getDate("from_date").toLocalDate());
		theProject.setEndDate(results.getDate("to_date").toLocalDate());
		return theProject;

	}
}