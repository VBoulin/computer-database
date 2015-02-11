package com.excilys.formation.java.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.PageWrapper;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.service.CompanyDBService;

@Service
@Transactional
public class CompanyDBServiceImpl implements CompanyDBService {

  @Autowired
  private CompanyDao  companyDao;
  @Autowired
  private ComputerDao computerDao;

  private Logger      logger = LoggerFactory.getLogger(CompanyDBServiceImpl.class);

  /**
   * Singleton : provide the access service to the database (company)
   */
  public CompanyDBServiceImpl() {}

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true)
  public Company getOne(Long id) {
    if (id == null || id < 1) {
      logger.error("Error with getOne()");
      throw new IllegalArgumentException("id cannot be null or negative");
    }
    return companyDao.findOne(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true)
  public PageWrapper<Company> createPage(PageWrapper<Company> page) {
    if (page == null) {
      logger.error("Error with createPage()");
      throw new IllegalArgumentException("Page cannot be null");
    }
    Page<Company> pageCompany;
    
    Sort sort;
    if(page.getOrder().getOrder().equals("ASC")) {
      sort = new Sort(Sort.Direction.ASC, page.getSort().getColumn());
    } else {
      sort = new Sort(Sort.Direction.DESC, page.getSort().getColumn());
    }
    int nbResultTotal = companyDao.countByNameContaining(page.getSearch());
    page.setNbResults(nbResultTotal);

    PageRequest pr = new PageRequest(page.getPageNumber()-1, page.getNbResultsPerPage(), sort);
    pageCompany = companyDao.findByNameContaining(page.getSearch(), pr);

    page.setList(pageCompany.getContent());
    return page;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true)
  public List<Company> getAll() {
    return (List<Company>) companyDao.findAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Long id) {
    if (id == null || id < 1) {
      logger.error("Error with delete.");
      throw new IllegalArgumentException("id cannot be null or negative");
    }
    
    computerDao.deleteByCompanyId(id);
    companyDao.delete(id);

  }

}
