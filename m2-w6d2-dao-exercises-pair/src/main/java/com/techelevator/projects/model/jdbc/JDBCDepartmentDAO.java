package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;
import com.techelevator.projects.model.Employee;

public class JDBCDepartmentDAO implements DepartmentDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Department> getAllDepartments() {

		ArrayList<Department> departments = new ArrayList<>();
		Department theDepartment = null;
		String sqlgetAllDepartments = " SELECT department_id, name " +
				" FROM department";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetAllDepartments);
		while(results.next()) {
			theDepartment = mapRowToDepartment(results);
			departments.add(theDepartment);
		}
		return departments;

	}


	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		ArrayList<Department> departments = new ArrayList<>();
		Department theDepartment = null;
		String sqlsearchDepartmentByName = " SELECT department_id, name " +
				" FROM department " + " WHERE name = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlsearchDepartmentByName, nameSearch);
		while(results.next()) {
			theDepartment = mapRowToDepartment(results);
			departments.add(theDepartment);
		}
		return departments;

	}


	@Override
	public void updateDepartmentName(Long departmentId, String departmentName) { //DataIntegrityViolationException
		String sqlupdateDepartmentName = " UPDATE department " +
				" SET name = ? " + " WHERE department_id = ? ";
		jdbcTemplate.queryForRowSet(sqlupdateDepartmentName, departmentName, departmentId);

	}

	@Override
	public Department createDepartment(String departmentName) {
		Department theDepartment = new Department();
		String sqlcreateDepartment = " INSERT INTO department(name, department_id) " +
				" VALUES(?, ?) ";
		theDepartment.setId(getNextDepartmentId());
		jdbcTemplate.update(sqlcreateDepartment, departmentName, theDepartment.getId());
		return theDepartment;
	}

	@Override
	public Department getDepartmentById(Long id) {
		return null;
	}
	private Department mapRowToDepartment(SqlRowSet results) {
		Department theDepartment;
		theDepartment = new Department();
		theDepartment.setId(results.getLong("department_id"));
		theDepartment.setName(results.getString("name"));


		return theDepartment;

	}
	private long getNextDepartmentId() {
		SqlRowSet nextIdResult= jdbcTemplate.queryForRowSet("SELECT nextval('seq_department_id')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		}else {
			throw new RuntimeException("Something went wrong getting Id for new Department");
		}
	}
}