package com.excilys.formation.java.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.service.ComputerDBService;

public class MockComputerDBService implements ComputerDBService {

  private ComputerDao computerDao;

  private Logger logger = LoggerFactory.getLogger(MockComputerDBService.class);

  public MockComputerDBService(ComputerDao computerDao) {
    this.computerDao = computerDao;
  }

  /**
   * Return one computer
   */
  @Override
  public Computer getOne(Long id) {
    if (id == null || id < 1) {
      logger.error("Error with getOne()");
      throw new IllegalArgumentException("id cannot be null or negative");
    }
    return computerDao.getOne(id);
  }

  /**
   * Create one computer
   */
  @Override
  public void create(Computer c) {
    if (c == null) {
      logger.error("Error with create()");
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
      logger.error("Error with update()");
      throw new IllegalArgumentException("Computer cannot be null");
    }
    computerDao.update(c);
  }

  /**
   * delete one computer 
   */
  @Override
  public void delete(Long id) {
    if (id == null || id < 1) {
      logger.error("Error with delete()");
      throw new IllegalArgumentException("Id cannot be null or negative");
    }
    computerDao.delete(id);
  }

  /**
   * return a page containing 10 computers
   */
  @Override
  public Page<Computer> createPage(Page<Computer> page) {
    if (page == null) {
      logger.error("Error with createPage()");
      throw new IllegalArgumentException("Page cannot be null");
    }
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
