package com.excilys.formation.java.service.impl;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.service.CompanyDBService;

public class CompanyDBServiceImpl implements CompanyDBService {

  private CompanyDao                        companyDao;

  private DaoFactory                        daoFactory;

  private final static CompanyDBServiceImpl service = new CompanyDBServiceImpl();

  /**
   * Singleton : provide the access service to the database (company)
   */
  private CompanyDBServiceImpl() {
    daoFactory = DaoFactory.getInstance();

    companyDao = daoFactory.getCompanyDao();
  }

  public static CompanyDBServiceImpl getInstance() {
    return service;
  }

  /**
   * return one company
   */
  @Override
  public Company getOne(Long id) {
    return companyDao.getOne(id);
  }

  /**
   * Return a page containing 10 companies
   */
  @Override
  public Page<Company> createPage(Page<Company> page) {
    return companyDao.createPage(page);
  }

}
