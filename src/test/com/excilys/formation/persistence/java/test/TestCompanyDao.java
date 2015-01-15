package com.excilys.formation.persistence.java.test;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.mapper.impl.CompanyRowMapperImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestCompanyDao{

  //Connections informations 
  private static final String URL      = "jdbc:mysql://localhost:3306/test-computer-database-db";
  private static final String USR      = "admintestcdb";
  private static final String PASSWORD = "qwerty12345";

  private MockCompanyDao companyDao;
  List<Company> list;
  
  @Before
  public void setUp() throws SQLException {
     companyDao = new MockCompanyDao();
     list = new ArrayList<Company>();
     list.add(new Company(1l, "Apple Inc."));
     list.add(new Company(2l, "Thinking Machines"));
     list.add(new Company(3l, "RCA"));
     list.add(new Company(4l, "Netronics"));
     list.add(new Company(5l, "Tandy Corporation"));
     list.add(new Company(6l, "Commodore International"));
     list.add(new Company(7l, "MOS Technology"));
     list.add(new Company(8l, "Micro Instrumentation and Telemetry Systems"));
     list.add(new Company(9l, "IMS Associates, Inc."));
     list.add(new Company(10l, "Digital Equipment Corporation"));
  }
  
  /**
   * test the getOne() method
   */
  @Test
  public void testGetOne() {
      Assert.assertEquals(Company.builder().id(1l).name("Apple Inc.").build(),companyDao.getOne(1l));
  }
  
  @Test
  public void testCreatepage() {
      Page<Company> page = new Page<Company>();
      
      Page<Company> pageR = new Page<Company>();
      pageR.setNbResultsPerPage(10);
      pageR.setPageNumber(1);
      pageR.setNbResults(42);
      pageR.setList(list);
      Assert.assertEquals(pageR, companyDao.createPage(page));
  }


}
