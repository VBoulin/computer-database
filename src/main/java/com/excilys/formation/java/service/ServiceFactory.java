package com.excilys.formation.java.service;

import com.excilys.formation.java.service.impl.CompanyDBServiceImpl;
import com.excilys.formation.java.service.impl.ComputerDBServiceImpl;

public enum ServiceFactory {
  
  INSTANCE;

  CompanyDBService companyDBService;
  ComputerDBService computerDBService;

  private ServiceFactory() {
    companyDBService = CompanyDBServiceImpl.getInstance();
    computerDBService = ComputerDBServiceImpl.getInstance();
  }

  public static ServiceFactory getInstance() {
    return INSTANCE;
  }

  /**
   * Return an instance of CompanyDBService
   * @return instance of CompanyDBService
   */
  public CompanyDBService getCompanyDBService() {
    return companyDBService;
  }

  /**
   * Return an instance of ComputerDBService
   * @return instance of ComputerDBService
   */
  public ComputerDBService getComputerDBService() {
    return computerDBService;
  }
  
}
