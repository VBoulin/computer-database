package com.excilys.formation.persistence.java.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;

public class MockComputerDao implements ComputerDao{
  
  //Connections informations 
  private static final String URL      = "jdbc:mysql://localhost:3306/test-computer-database-db";
  private static final String USR      = "admintcdb";
  private static final String PASSWORD = "qwerty12345";

  private Logger logger = LoggerFactory.getLogger("com.excilys.formation.persistence.java.test.MockComputerDao");
  
  private final static MockComputerDao computerDao = new MockComputerDao();

  /**
   * Singleton : provide the access service to the database (computer)
   */
  private MockComputerDao() {}

  public static MockComputerDao getInstance() {
    return computerDao;
  }

  @Override
  public Computer getOne(Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void create(Computer computer) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void update(Computer computer) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Page<Computer> createPage(Page<Computer> page) {
    // TODO Auto-generated method stub
    return null;
  }

}
