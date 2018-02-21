package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.projects.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCDepartmentDAO implements DepartmentDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Department> getAllDepartments() {
		ArrayList<Department> department = new ArrayList<>();
		Department theDepartment = null;
		String sqlgetAllDepartments = " SELECT department_id, name " +
				" FROM department ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetAllDepartments);
		while(results.next()) {
			theDepartment = mapRowToDepartment(results);
			department.add(theDepartment);
		}
		return department;

	}

	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		return new ArrayList<>();
	}

	@Override
	public void updateDepartmentName(Long departmentId, String departmentName) {
		
	}

	@Override
	public Department createDepartment(String departmentName) {
		return null;
	}

	@Override
	public Department getDepartmentById(Long id) {

		Department theDepartment = null;
		String sqlgetDepartmentById = " SELECT department_id, name " +
				" FROM department " +
				" WHERE department_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetDepartmentById, id);
		if(results.next()) {
			theDepartment = mapRowToDepartment(results);
		}
		return theDepartment;
	}


	private Department mapRowToDepartment(SqlRowSet results) {
		Department theDepartment;
		theDepartment = new Department();
		theDepartment.setId(results.getLong("department_id"));
		theDepartment.setName(results.getString("name"));

		return theDepartment;

	}

}
