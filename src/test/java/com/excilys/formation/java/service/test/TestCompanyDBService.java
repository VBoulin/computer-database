package com.excilys.formation.java.service.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.service.CompanyDBService;

@RunWith(MockitoJUnitRunner.class)
public class TestCompanyDBService {

  CompanyDBService companyDBService;
  CompanyDao       companyDao;
  Page<Company>    page;
  Page<Company>    pageR;

  @Before
  public void setUp() {
    companyDao = Mockito.mock(CompanyDao.class);
    companyDBService = new MockCompanyDBService(companyDao);

    page = new Page<Company>();
    pageR = new Page<Company>(1, new ArrayList<Company>(), 10, 12);

    Mockito.when(companyDao.getOne(1l)).thenReturn(Company.builder().id(1l).name("truc").build());
    Mockito.when(companyDao.createPage(page)).thenReturn(pageR);
  }

  /**
   * test the getOne() metjod
   */
  @Test
  public void testGetOne() {
    Assert.assertEquals(Company.builder().id(1L).name("truc").build(), companyDBService.getOne(1L));
  }

  /**
   * test the createpage() method
   */
  @Test
  public void testCreatePage() {
    Assert.assertEquals(pageR, companyDBService.createPage(page));
  }
}
