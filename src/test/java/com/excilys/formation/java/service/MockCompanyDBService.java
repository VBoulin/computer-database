//package com.excilys.formation.java.service;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.excilys.formation.java.model.Company;
//import com.excilys.formation.java.model.Page;
//import com.excilys.formation.java.persistence.CompanyDao;
//import com.excilys.formation.java.service.CompanyDBService;
//
//public class MockCompanyDBService implements CompanyDBService {
//
//  private CompanyDao companyDao;
//
//  private Logger logger = LoggerFactory.getLogger(MockCompanyDBService.class);
//
//  public MockCompanyDBService(CompanyDao companyDAO) {
//    this.companyDao = companyDAO;
//  }
//
//  @Override
//  public Company getOne(Long id) {
//    if (id == null || id < 1) {
//      logger.error("Error with getOne()");
//      throw new IllegalArgumentException("id cannot be null or negative");
//    }
//    return companyDao.getOne(id);
//  }
//
//  @Override
//  public Page<Company> createPage(Page<Company> page) {
//    if (page == null) {
//      logger.error("Error with createPage()");
//      throw new IllegalArgumentException("Page cannot be null");
//    }
//    return companyDao.createPage(page);
//  }
//
//  @Override
//  public List<Company> getAll() {
//    return companyDao.getAll();
//  }
//
//  @Override
//  public void delete(Long id) {
//    
//  }
//}
