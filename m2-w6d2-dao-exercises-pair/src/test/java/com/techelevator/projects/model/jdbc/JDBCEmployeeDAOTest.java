package com.techelevator.projects.model.jdbc;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.projects.model.Department;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import static org.junit.Assert.*;

import static org.junit.Assert.*;

public class JDBCEmployeeDAOTest {

    private static SingleConnectionDataSource dataSource;
    private JDBCEmployeeDAO dao;
    private JDBCDepartmentDAO departmentDAO;
    private JDBCProjectDAO projectDAO;

    @BeforeClass
    public static void setupDataSource() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        /* The following line disables autocommit for connections
         * returned by this DataSource. This allows us to rollback
         * any changes after each test */
        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void closeDataSource() throws SQLException {
        dataSource.destroy();
    }

    @Before
    public void setup() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JDBCEmployeeDAO(dataSource);
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Test
    public void getAllEmployees() {
        List<Employee> results = dao.getAllEmployees();

        assertNotNull(results);
        assertEquals(12, results.size());
    }

    @Test
    public void searchEmployeesByName() {
        Employee testEmployee = dao.createEmployee("Frank", "Stallone");
        List<Employee> results = dao.searchEmployeesByName("Frank", "Stallone");

        assertNotNull(results);
        assertEquals(1, results.size());
        Employee savedEmployee = results.get(0);
        assertEquals(testEmployee, savedEmployee);
    }

    @Test
    public void change_employee_department() {

        List<Employee> results = dao.getEmployeesByDepartmentId(1);
        Employee testEmployee = results.get(0);

        dao.changeEmployeeDepartment(testEmployee.getId(), (long) 2);
        List<Employee> results2 = dao.getEmployeesByDepartmentId(2);

        Employee savedEmployee = results2.get(results2.size()-1);

       assertEquals(testEmployee, savedEmployee);

    }



    @Test
    public void get_employee_by_project_id() {
        List<Employee> results = dao.getEmployeesByProjectId((long) 3);
        Employee testEmployee = results.get(0);

        assertNotNull(results);
        assertEquals(4, results.size());
        Employee savedEmployee = results.get(0);
        assertEquals(testEmployee, savedEmployee);

    }

//    @Test
//    public void testing() {
//        Employee testEmployee = dao.createEmployee("Frank", "Stallone");
//        testEmployee.setId((long) 100);
//
//        Project testProject = projectDAO.createProject("DAO Testing Project");
//
//
//        List<Employee> results = dao.searchEmployeesByName("Frank", "Stallone");
//        testEmployee.setId((long) 100);
//
//        projectDAO.addEmployeeToProject((long) 3, testEmployee.getId());
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        Employee savedEmployee = results.get(0);
//        assertEquals(testEmployee, savedEmployee);
//    }

    @Test
    public void getEmployeesWithoutProjects() {

    }

    @Test
    public void changeEmployeeDepartment() {
    }
}