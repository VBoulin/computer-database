package com.excilys.formation.java.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.service.ComputerDBService;

public class ComputerDBServiceImpl implements ComputerDBService {

  private Logger logger = LoggerFactory.getLogger("com.excilys.formation.java.service.impl.CompanyDBServiceImpl");
  
  private ComputerDao                        computerDao;

  private DaoFactory                         daoFactory;

  private final static ComputerDBServiceImpl service = new ComputerDBServiceImpl();

  /**
   * Singleton : provide the access service to the database
   */
  private ComputerDBServiceImpl() {
    daoFactory = DaoFactory.getInstance();

    computerDao = daoFactory.getComputerDao();
  }

  public static ComputerDBServiceImpl getInstance() {
    return service;
  }

  /**
   * Return one computer
   */
  @Override
  public Computer getOneComputer(Long id) {
    return computerDao.getOne(id);
  }

  /**
   * Create one computer
   */
  @Override
  public void createComputer(Computer c) {
    if (c == null) {
      logger.error("SQLError with create()");
      throw new IllegalArgumentException("Computer cannot be null");
    }
    computerDao.create(c);
  }

  /**
   * update one computer
   */
  @Override
  public void updateComputer(Computer c) {
    if (c == null) {
      logger.error("SQLError with update()");
      throw new IllegalArgumentException("Computer cannot be null");
    }
    computerDao.update(c);
  }

  /**
   * delete one computer 
   */
  @Override
  public void deleteComputer(Long id) {
    if (id == null) {
      logger.error("SQLError with delete()");
      throw new IllegalArgumentException("Id cannot be null");
    }
    computerDao.delete(id);
  }

  /**
   * return a page containing 10 computers
   */
  @Override
  public Page<Computer> getPagedListComputer(Page<Computer> page) {
    return computerDao.getPagedList(page);
  }

}
