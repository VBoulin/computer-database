package com.excilys.formation.java.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.formation.java.model.Company;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-persistenceContext.xml"})
public class TestCompanyDao {

    private static final String PATH = "src/test/resources/dbInit.sql";
  
    @Autowired
    CompanyDao companyDao;
    
    List<Company> companies;
    Company company1 = new Company(1L, "Apple Inc.");
    Company company2 = new Company(2L, "Thinking Machines");
    
    @Autowired
    DataSource dataSource;
    
    Connection conn;
    
    @Before
    public void init() throws SQLException, IOException {
        companies = new ArrayList<Company>();
        companies.add(company1);
        companies.add(company2);

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
        assertEquals(companies, companyDao.findAll());
    }
    
    /*
     * Tests of the findOne function
     */
    @Test
    public void findOne() {
        assertEquals(new Company(1L, "Apple Inc."), companyDao.findOne(1L));
    }
    
    @Test
    public void FindOneInvalid() {
        assertNull(companyDao.findOne(-1L));
    }  
    
    /*
     * Tests of the delete function
     */
    
    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteInvalidId() {
        companyDao.delete(-1L);
        assertEquals(companies, companyDao.findAll());
    }
    
    
}