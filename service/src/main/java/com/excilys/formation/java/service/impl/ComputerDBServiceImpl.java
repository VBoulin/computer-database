package com.excilys.formation.java.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.service.ComputerDBService;

@Service
@Transactional
public class ComputerDBServiceImpl implements ComputerDBService {

  private Logger      logger = LoggerFactory.getLogger(ComputerDBServiceImpl.class);

  @Autowired
  private ComputerDao computerDao;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly=true)
  public Computer getOne(Long id) {
    if (id == null || id < 1) {
      logger.error("Error with getOne()");
      return null;
    }
    return computerDao.findOne(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly=true)
  public List<Computer> getAll() {
    return (List<Computer>) computerDao.findAll();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly=true)
  public Page<Computer> createPage(String search, Pageable page) {
    if (page == null) {
      logger.error("Error with createPage()");
      return null;
    }
    return computerDao.findByNameContainingOrCompanyNameContaining(search, search, page);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create(Computer c) {
    if (c == null) {
      logger.error("Error with create()");
    } else {
      computerDao.save(c);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(Computer c) {
    if (c == null) {
      logger.error("Error with update()");
    } else {
      computerDao.save(c);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Long id) {
    if (id == null || id < 1) {
      logger.error("Error with delete()");
    } else {
      computerDao.delete(id);
    }
  }
}
