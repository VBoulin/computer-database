package com.excilys.formation.java.service.test;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.service.CompanyDBService;

public class MockCompanyDBService implements CompanyDBService{

  private CompanyDao companyDao;
  
  public MockCompanyDBService(CompanyDao companyDAO) {
      this.companyDao = companyDAO;
  }

  @Override
  public Company getOne(Long id) {
    return companyDao.getOne(id);
  }

  @Override
  public Page<Company> createPage(Page<Company> page) {
    return companyDao.createPage(page);
  }
}
