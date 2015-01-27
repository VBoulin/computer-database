package com.excilys.formation.java.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.service.ComputerDBService;

@Service
public class ComputerDBServiceImpl implements ComputerDBService {

  private Logger      logger = LoggerFactory.getLogger(ComputerDBServiceImpl.class);

  @Autowired
  private ComputerDao computerDao;
  @Autowired
  private DaoFactory  daoFactory;

  /**
   * Singleton : provide the access service to the database
   */
  public ComputerDBServiceImpl() {
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  @Override
  public List<Computer> getAll() {
    return computerDao.getAll();
  }

}
