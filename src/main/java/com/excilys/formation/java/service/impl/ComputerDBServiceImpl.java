package com.excilys.formation.java.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.service.ComputerDBService;

public enum ComputerDBServiceImpl implements ComputerDBService {
  
  INSTANCE;

  private Logger logger = LoggerFactory.getLogger(ComputerDBServiceImpl.class);
  
  private ComputerDao                        computerDao;

  private DaoFactory                         daoFactory;

  /**
   * Singleton : provide the access service to the database
   */
  private ComputerDBServiceImpl() {
    daoFactory = DaoFactory.getInstance();

    computerDao = daoFactory.getComputerDao();
  }

  public static ComputerDBServiceImpl getInstance() {
    return INSTANCE;
  }

  /**
   * Return one computer
   */
  @Override
  public Computer getOne(Long id) {
    return computerDao.getOne(id);
  }

  /**
   * Create one computer
   */
  @Override
  public void create(Computer c) {
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
  public void update(Computer c) {
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
  public void delete(Long id) {
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
  public Page<Computer> createPage(Page<Computer> page) {
    return computerDao.createPage(page);
  }

  /**
   * return a list containing all the computers
   */
  @Override
  public List<Computer> getAll() {
    return computerDao.getAll();
  }

}
