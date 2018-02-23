package com.techelevator.projects.model.jdbc;


import com.techelevator.projects.model.Department;
import org.junit.*;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class JDBCDepartmentDAOTest {

    /* Using this particular implementation of DataSource so that
     * every database interaction is part of the same database
     * session and hence the same database transaction */
    private static SingleConnectionDataSource dataSource;
    private JDBCDepartmentDAO dao;


    /* Before any tests are run, this method initializes the datasource for testing. */
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

    /* After all tests have finished running, this method will close the DataSource */
    @AfterClass
    public static void closeDataSource() throws SQLException {
        dataSource.destroy();
    }

    @Before
    public void setup() {
        dao = new JDBCDepartmentDAO(dataSource);

    }

    /* After each test, we rollback any changes that were made to the database so that
     * everything is clean for the next test */
    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Test
    public void save_new_Department_and_read_it_back() {

        Department testDepartment = dao.createDepartment("DAO Test Department");
        Department savedDepartment = dao.getDepartmentById(testDepartment.getId());
        assertNotEquals(null, testDepartment.getId());
        assertEquals(testDepartment, savedDepartment);
    }

    @Test
    public void createDepartment() {
        dao.createDepartment("DAO Test Department");
        System.out.println(dao.getAllDepartments());
    }


    @Test
    public void returns_departments_by_name() {

        Department testDepartment = dao.createDepartment("DAO Test Department");

        List<Department> results = dao.searchDepartmentsByName("DAO Test Department");

        assertNotNull(results);
        assertEquals(1, results.size());
        Department savedDepartment = results.get(0);
        assertEquals(testDepartment, savedDepartment);

    }


    @Test
    public void update_department_name() {

        Department testDepartment = dao.createDepartment("DAO Test Department");
        dao.updateDepartmentName(testDepartment.getId(), "DAO Test Department2");

        Department savedDepartment = dao.getDepartmentById(testDepartment.getId());

        Assert.assertEquals("DAO Test Department2", savedDepartment.getName());

    }

}