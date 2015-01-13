package com.excilys.formation.java.service;

import java.util.List;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.persistence.DaoFactory;

public class Service implements ServiceInterface {

  private CompanyDao           companyDao;
  private ComputerDao          computerDao;

  private DaoFactory           daoFactory;

  private final static Service service = new Service();

  private Service() {
    daoFactory = DaoFactory.getInstance();

    computerDao = daoFactory.getComputerDao();
    companyDao = daoFactory.getCompanyDao();
  }

  public static Service getInstance() {
    return service;
  }

  @Override
  public List<Computer> getAllComputers() {
    // TODO Auto-generated method stub
    return computerDao.getAll();
  }

  @Override
  public List<Company> getAllCompanies() {
    // TODO Auto-generated method stub
    return companyDao.getAll();
  }

  @Override
  public Computer getOneComputer(Long id) {
    // TODO Auto-generated method stub
    return computerDao.getOne(id);
  }

  @Override
  public Company getOneCompany(Long id) {
    // TODO Auto-generated method stub
    return companyDao.getOne(id);
  }

  @Override
  public void createComputer(Computer c) {
    // TODO Auto-generated method stub
    if (c == null) {
      throw new IllegalArgumentException("Computer cannot be null");
    }
    computerDao.create(c);
  }

  @Override
  public void updateComputer(Computer c) {
    // TODO Auto-generated method stub
    if (c == null) {
      throw new IllegalArgumentException("Computer cannot be null");
    }
    computerDao.update(c);
  }

  @Override
  public void deleteComputer(Long id) {
    // TODO Auto-generated method stub
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    computerDao.delete(id);
  }

}
