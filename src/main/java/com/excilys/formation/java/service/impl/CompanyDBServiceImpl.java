package com.excilys.formation.java.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.service.CompanyDBService;

public enum CompanyDBServiceImpl implements CompanyDBService {
  
  INSTANCE;

  private CompanyDao                        companyDao;

  private Logger logger = LoggerFactory.getLogger(CompanyDBServiceImpl.class);
  
  private DaoFactory                        daoFactory;

  /**
   * Singleton : provide the access service to the database (company)
   */
  private CompanyDBServiceImpl() {
    daoFactory = DaoFactory.INSTANCE;

    companyDao = daoFactory.getCompanyDao();
  }

  /**
   * return one company
   */
  @Override
  public Company getOne(Long id) {
    if (id == null || id < 1) {
      logger.error("Error with getOne()");
      throw new IllegalArgumentException("id cannot be null or negative");
    }
    return companyDao.getOne(id);
  }

  /**
   * Return a page containing 10 companies
   */
  @Override
  public Page<Company> createPage(Page<Company> page) {
    if (page == null) {
      logger.error("Error with createPage()");
      throw new IllegalArgumentException("Page cannot be null");
    }
    return companyDao.createPage(page);
  }

  /**
   * return a list containing all the companies
   */
  @Override
  public List<Company> getAll() {
    return companyDao.getAll();
  }

}
