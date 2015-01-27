package com.excilys.formation.java.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.service.CompanyDBService;

@Service
public class CompanyDBServiceImpl implements CompanyDBService {

  @Autowired
  private CompanyDao  companyDao;
  @Autowired
  private ComputerDao computerDao;

  private Logger      logger = LoggerFactory.getLogger(CompanyDBServiceImpl.class);

  @Autowired
  private DaoFactory  daoFactory;

  /**
   * Singleton : provide the access service to the database (company)
   */
  public CompanyDBServiceImpl() {
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  @Override
  public List<Company> getAll() {
    return companyDao.getAll();
  }

  /**
   * {@inheritDoc}
   */
  public void delete(Long id) {
    if (id == null || id < 1) {
      logger.error("Error with delete.");
      throw new IllegalArgumentException("id cannot be null or negative");
    }
    try {
      daoFactory.startTransactionalConnection();
      
      computerDao.deleteByCompany(id);
      companyDao.delete(id);
      
      daoFactory.commitTransactionalConnection();
    } finally {
      daoFactory.closeTransactionalConnection();
    }
  }

}
