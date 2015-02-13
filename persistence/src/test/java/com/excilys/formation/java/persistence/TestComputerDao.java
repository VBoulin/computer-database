package com.excilys.formation.java.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-persistenceContext.xml"})
public class TestComputerDao {
    
    private static final String PATH = "src/test/resources/dbInit.sql";
    
    @Autowired
    ComputerDao computerDao;
    
    @Autowired
    DataSource dataSource;
    
    List<Computer> list;
    Company company1 = new Company(1L, "Apple Inc.");
    Company company2 = new Company(2L, "Thinking Machines");
    
    Connection conn;
    
    @Before
    public void before() throws SQLException, IOException {
        list = new ArrayList<Computer>();
        list.add(new Computer(1L, "MacBook Pro 15.4 inch", null, null, company1));
        list.add(new Computer(2L, "MacBook Pro", LocalDate.parse("2006-01-10"), null, company1));
        list.add(new Computer(3L, "CM-2a", null, null, company2));
        list.add(new Computer(4L, "CM-200", null, null, company2));
        
        conn = DataSourceUtils.getConnection(dataSource);
        
        InputStream scriptInputStream = new FileInputStream(new File(PATH));
        
        conn = DataSourceUtils.getConnection(dataSource);

        SqlRunner.runScript(conn, scriptInputStream);
    }
    
    @After
    public void after() throws SQLException{
      conn.close();
    }
    /*
     * Tests of the findAll function
     */
    @Test
    public void findAll() {
        assertEquals(list, computerDao.findAll());
    }
    
    /*
     * Tests of the findOne function
     */
    @Test
    public void findOne() {
        assertEquals(list.get(0), computerDao.findOne(1L));
    }
    
    /*
     * Tests of the create function
     */
    @Test
    public void create() {
        Computer computer = Computer.builder().name("test").introduced(LocalDate.parse("1993-01-10")).company(company1).build();
        
        computerDao.save(computer);
        computer.setId(5L);
        assertEquals(computer, computerDao.findOne(5L));
    }
    
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void createNull() {
        Computer computer = null;
        computerDao.save(computer);
    }
    
    @Test
    public void createEmptyComputer() {
        computerDao.save(new Computer());
        list.add(Computer.builder().id(5L).build());
        assertEquals(list, computerDao.findAll());
    }
    
    /*
     * Tests of the update function
     */
    @Test
    public void update() {
        Computer computer = Computer.builder().id(2L).name("test").introduced(LocalDate.parse("1993-01-12")).build();
        computerDao.save(computer);
        assertEquals(computer, computerDao.findOne(2L));
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void updateNull() {
        Computer computer = null;
        computerDao.save(computer);
    }
    
    @Test
    public void updateInvalidId() {
        Computer computer = new Computer();
        computer.setId(-1L);
        computerDao.save(computer);
        computer.setId(5L);
        list.add(computer);
        assertEquals(list, computerDao.findAll());
    }
    
    @Test(expected = JpaObjectRetrievalFailureException.class)
    public void updateInvalidCompanyId() {
        Computer computer = new Computer();
        computer.setId(1L);
        computer.setCompany(new Company(-1L, ""));
        computerDao.save(computer);
    }
    
    /*
     * Tests of the delete function
     */
    @Test
    public void delete() {
        assertNotNull(computerDao.findOne(2L));
        computerDao.delete(2L);
        assertNull(computerDao.findOne(2L));
    }
    
    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteInvalidId() {
        computerDao.delete(-1L);
    }
    
    /*
     * Tests of the deleteByCompanyId function
     */
    
    @Test
    public void DeleteCompanyInvalid() {
        computerDao.deleteByCompanyId(-1L);
        assertEquals(list, computerDao.findAll());
    }
}