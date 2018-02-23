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

public class JDBCProjectDAOTest {

    private static SingleConnectionDataSource dataSource;
    private JDBCProjectDAO dao;
    private JDBCDepartmentDAO departmentDAO;
    private JDBCEmployeeDAO employeeDAO;

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
        dao = new JDBCProjectDAO(dataSource);
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Test
    public void get_all_active_projects() {
        Project testProject = dao.createProject("DAO Test Project");

       List<Project> results = dao.getAllActiveProjects();

        assertNotNull(results);
        assertEquals(4, results.size());
        Project savedProject = results.get(3);
        assertEquals(testProject, savedProject);

    }

    @Test
    public void add_employee_to_project() {
        Employee testEmployee = employeeDAO.createEmployee("Frank", "Stallone");


    }

    @Test
    public void getEmployeesByDepartmentId() {
    }

    @Test
    public void getEmployeesWithoutProjects() {
    }

    @Test
    public void getEmployeesByProjectId() {
    }

    @Test
    public void changeEmployeeDepartment() {
    }
}