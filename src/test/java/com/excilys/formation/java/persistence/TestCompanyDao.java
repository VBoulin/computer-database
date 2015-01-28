package com.excilys.formation.java.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;

@RunWith(MockitoJUnitRunner.class)
public class TestCompanyDao {

  private MockCompanyDao companyDao;
  List<Company>          companies;

  @Before
  public void setUp() throws SQLException {
    companyDao = new MockCompanyDao();
    companies = new ArrayList<Company>();
    companies.add(new Company(1l, "Apple Inc."));
    companies.add(new Company(2l, "Thinking Machines"));
    companies.add(new Company(3l, "RCA"));
    companies.add(new Company(4l, "Netronics"));
    companies.add(new Company(5l, "Tandy Corporation"));
    companies.add(new Company(6l, "Commodore International"));
    companies.add(new Company(7l, "MOS Technology"));
    companies.add(new Company(8l, "Micro Instrumentation and Telemetry Systems"));
    companies.add(new Company(9l, "IMS Associates, Inc."));
    companies.add(new Company(10l, "Digital Equipment Corporation"));
  }

  /**
   * test the getOne() method
   */
  @Test
  public void testGetOne() {
    Assert.assertEquals(Company.builder().id(1l).name("Apple Inc.").build(), companyDao.getOne(1l));

    try {
      companyDao.getOne(null);
      Assert.fail("Should throw exception when the id is null");
    } catch (PersistenceException aExp) {
      assert (aExp.getMessage().contains("NullError"));
    }
  }

  @Test
  public void testCreatepage() {
    Page<Company> page = new Page<Company>();

    Page<Company> pageR = new Page<Company>();
    pageR.setNbResultsPerPage(10);
    pageR.setPageNumber(1);
    pageR.setNbResults(42);
    pageR.setList(companies);
    Assert.assertEquals(pageR, companyDao.createPage(page));

    try {
      companyDao.createPage(null);
      Assert.fail("Should throw exception when the page is null");
    } catch (PersistenceException aExp) {
      assert (aExp.getMessage().contains("NullError"));
    }
  }

}
