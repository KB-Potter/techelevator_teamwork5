package com.techelevator.projects.model.jdbc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.EmployeeDAO;


public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<>();
		Employee theEmployee = null;
		String sqlgetAllEmployees = " SELECT department_id, employee_id, first_name, last_name, birth_date, gender, hire_date" +
				" FROM employee";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetAllEmployees);
		while(results.next()) {
			theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
		}
		return employees;

	}
	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		ArrayList<Employee> employees = new ArrayList<>();
		Employee theEmployee = null;
		String sqlsearchEmployeesByName = " SELECT department_id, employee_id, first_name, last_name, birth_date, gender, hire_date" +
				" FROM employee " + " WHERE first_name = ? " + " AND last_name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlsearchEmployeesByName, firstNameSearch, lastNameSearch);
		while(results.next()) {
			theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
		}
		return employees;

	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
		ArrayList<Employee> employees = new ArrayList<>();
		Employee theEmployee = null;
		String sqlsearchEmployeesByDepartmentId = " SELECT department_id, employee_id, first_name, last_name, birth_date, gender, hire_date" +
				" FROM employee " + " WHERE department_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlsearchEmployeesByDepartmentId, id);
		while(results.next()) {
			theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
		}
		return employees;

	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		ArrayList<Employee> employees = new ArrayList<>();
		Employee theEmployee = null;
		String sqlgetEmployeesWithoutProjects = " SELECT department_id, employee.employee_id, first_name, last_name, birth_date, gender, hire_date" +
				" FROM employee " + " FULL OUTER JOIN project_employee ON project_employee.employee_id = employee.employee_id " +
				" FULL OUTER JOIN project ON project.project_id = project_employee.project_id " +
				" WHERE project.project_id IS NULL ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetEmployeesWithoutProjects);
		while(results.next()) {
			theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		ArrayList<Employee> employees = new ArrayList<>();
		Employee theEmployee = null;
		String sqlgetEmployeesByProjectId = " SELECT department_id, employee.employee_id, first_name, last_name, birth_date, gender, hire_date" +
				" FROM employee " + " FULL OUTER JOIN project_employee ON project_employee.employee_id = employee.employee_id " +
				" FULL OUTER JOIN project ON project.project_id = project_employee.project_id " +
				" WHERE project.project_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlgetEmployeesByProjectId, projectId);
		while(results.next()) {
			theEmployee = mapRowToEmployee(results);
			employees.add(theEmployee);
		}
		return employees;
	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		String sqlChangeEmployeeDepartment = "UPDATE employee " +
				" SET department_id = ? " +
				" WHERE department_id IS NOT NULL AND employee_id = ? ";

		jdbcTemplate.update(sqlChangeEmployeeDepartment, departmentId, employeeId);
	}

	private Employee mapRowToEmployee(SqlRowSet results) {
		Employee theEmployee;
		theEmployee = new Employee();
		theEmployee.setId(results.getLong("employee_id"));
		theEmployee.setDepartmentId(results.getLong("department_id"));
		theEmployee.setFirstName(results.getString("first_name"));
		theEmployee.setLastName(results.getString("last_name"));
		theEmployee.setBirthDay(results.getDate("birth_date").toLocalDate());
		theEmployee.setGender(results.getString("gender").charAt(0));
		theEmployee.setHireDate(results.getDate("hire_date").toLocalDate());

		return theEmployee;
	}

	private long getNextEmployeeId() {
		SqlRowSet nextIdResult= jdbcTemplate.queryForRowSet("SELECT nextval('seq_employee_id')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		}else {
			throw new RuntimeException("Something went wrong getting Id for new Employee");
		}
	}
	public Employee createEmployee(String firstName, String lastName) {
		LocalDate locald = LocalDate.of(1967, 06, 22);
		Date date = Date.valueOf(locald);

		Employee theEmployee = new Employee();
		theEmployee.setFirstName(firstName);
		theEmployee.setLastName(lastName);
		theEmployee.setGender('M');
		String sqlcreateEmployee = " INSERT INTO Employee(first_name, last_name, birth_date, gender, hire_date) " +
				" VALUES(?, ?, ?, ?, ?) ";
		theEmployee.setId(getNextEmployeeId());
		jdbcTemplate.update(sqlcreateEmployee, firstName, lastName, date, theEmployee.getGender(), date);
		return theEmployee;
	}

//VALUES ('Fredrick', 'Keppard', '1953-07-15', 'M', '2001-04-01

}