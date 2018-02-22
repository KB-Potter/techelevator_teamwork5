package com.techelevator.projects.model.jdbc;


        import java.sql.SQLException;
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

public class JDBCDepartmentDAOTest {

    private static final String TEST_DEPARTMENT = "Testing Department";
    

    /* Using this particular implementation of DataSource so that
     * every database interaction is part of the same database
     * session and hence the same database transaction */
    private static SingleConnectionDataSource dataSource;
    private JDBCDepartmentDAO dao;
    private long TEST_ID;
   

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
        String sqlInsertDepartment = "INSERT INTO department (name, department_id) VALUES (?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);


        dao = new JDBCDepartmentDAO(dataSource);
        long TEST_ID = dao.getNextDepartmentId();
        jdbcTemplate.update(sqlInsertDepartment, TEST_DEPARTMENT, TEST_ID);
    }

    /* After each test, we rollback any changes that were made to the database so that
     * everything is clean for the next test */
    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Test
    public void save_new_Department_and_read_it_back() throws SQLException {
        String TesterDept = "Testing Department2";

        Department testDepartment = dao.createDepartment(TesterDept);

        Department savedDepartment = dao.getDepartmentById(testDepartment.getId());


        assertNotEquals(null, testDepartment.getId());
        assertdepartmentsAreEqual(testDepartment, savedDepartment);
    }


    @Test
    public void return_all_departments() {
        String TesterDept = "Testing Department2";
        int beforeAdd = dao.getAllDepartments().size();

        Department testDepartment = dao.createDepartment(TesterDept);
//
        assertEquals(beforeAdd + 1, dao.getAllDepartments().size());


    }

    @Test
    public void returns_departments_by_dept_id() {

        String TesterDept = "Testing Department2";

        Department testDepartment = dao.createDepartment(TesterDept);

        Department savedDepartment = dao.getDepartmentById(testDepartment.getId());

        assertdepartmentsAreEqual(testDepartment, savedDepartment);
//
//  long daoLong = 0;
//        System.out.println(savedDepartment = dao.getDepartmentById(daoLong));

//        assertNotNull(results);
//        assertEquals(1, results.size());
//        Department savedDepartment = results.get(0);
//        assertdepartmentsAreEqual(testDepartment, savedDepartment);
    }
//
//    @Test
//    public void returns_multiple_departments_by_country_code() {
//
//        dao.save(getDepartment("SQL Station", "South Dakota", TEST_DEPARTMENT, 65535));
//        dao.save(getDepartment("Postgres Point", "North Dakota", TEST_DEPARTMENT, 65535));
//
//        List<Department> results = dao.findDepartmentByCountryCode(TEST_DEPARTMENT);
//
//        assertNotNull(results);
//        assertEquals(2, results.size());
//    }
//
//    @Test
//    public void returns_departments_by_district() {
//        String testDistrict = "Tech Elevator";
//        Department theDepartment = getDepartment("SQL Station", testDistrict, TEST_DEPARTMENT, 65535);
//        dao.save(theDepartment);
//
//        List<Department> results = dao.findDepartmentByDistrict(testDistrict);
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        Department savedDepartment = results.get(0);
//        assertdepartmentsAreEqual(theDepartment, savedDepartment);
//    }

    private Department getDepartment(String name, long id) {
        Department theDepartment = new Department();
        theDepartment.setName(name);
        theDepartment.setId(id);
        return theDepartment;
    }

    private boolean assertdepartmentsAreEqual(Department expected, Department actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());


    }



    @Test
    public void searchDepartmentsByName() {
    }

    @Test
    public void updateDepartmentName() {
    }

    @Test
    public void createDepartment() {
    }

    @Test
    public void getDepartmentById() {
    }

    @Test
    public void getNextDepartmentId() {
    }
}