package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.projects.model.Project;
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
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sqlremoveEmployeeFromProject = " DELETE FROM project_employee " +
				" WHERE project_id = ? AND employee_id = ?";
		jdbcTemplate.update(sqlremoveEmployeeFromProject, projectId, employeeId);
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String sqladdEmployeeToProject = "INSERT INTO project_employee (project_id, employee_id) " +
				" VALUES(?, ?) ";
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


	public Project createProject(String ProjectName) {
		Project theProject = new Project();
		theProject.setName(ProjectName);
		String sqlcreateProject = " INSERT INTO Project(name, Project_id, from_date, to_date) " +
				" VALUES(?, ?, ?, ?) ";
		LocalDate locald = LocalDate.of(1967, 06, 22);
		Date date = Date.valueOf(locald);
		theProject.setId(getNextProjectId());
		jdbcTemplate.update(sqlcreateProject, ProjectName, theProject.getId(), date, date);
		return theProject;
	}

	public long getNextProjectId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_project_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong getting Id for new Department");
		}
	}

}