package com.excilys.formation.java.service.test;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.service.ComputerDBService;

public class MockComputerDBService implements ComputerDBService {

  private ComputerDao computerDao;

  public MockComputerDBService(ComputerDao computerDao) {
    this.computerDao = computerDao;
  }

  @Override
  public Computer getOne(Long id) {
    return computerDao.getOne(id);
  }

  @Override
  public void create(Computer c) {
    computerDao.create(c);
  }

  @Override
  public void update(Computer c) {
    computerDao.update(c);
  }

  @Override
  public void delete(Long id) {
    computerDao.delete(id);
  }

  @Override
  public Page<Computer> createPage(Page<Computer> page) {
    return computerDao.createPage(page);
  }

}
