package com.excilys.formation.persistence.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.persistence.impl.CompanyDaoImpl;


public class TestCompanyDaoImpl{

  private CompanyDaoImpl companyDaoImpl;
  
  @Before
  public void setUp() {
    companyDaoImpl = CompanyDaoImpl.getInstance();
    
  }


}
