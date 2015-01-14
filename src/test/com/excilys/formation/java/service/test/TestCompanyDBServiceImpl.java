package com.excilys.formation.java.service.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.service.impl.CompanyDBServiceImpl;

public class TestCompanyDBServiceImpl {

  private CompanyDBServiceImpl companyDBService;
  private CompanyDao           companyDao;

  @Before
  public void setUp() {
    companyDBService = CompanyDBServiceImpl.getInstance();
    companyDao = Mockito.mock(CompanyDao.class);

    Mockito.when(companyDao.getOne(5l)).thenReturn(new Company(5l, "Company5"));
    Mockito.when(companyDao.getOne(3l)).thenReturn(new Company(3l, "Company3"));
    Mockito.when(companyDao.getOne(1l)).thenReturn(new Company(1l, "Company1"));
  }

  @Test
  public void testGetOne() {
    Assert.assertEquals((new Company(5l, "Company5")), companyDBService.getOne(5l));
  }
}
