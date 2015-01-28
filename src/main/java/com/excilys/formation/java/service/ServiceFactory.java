package com.excilys.formation.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {

  @Autowired
  CompanyDBService companyDBService;
  @Autowired
  ComputerDBService computerDBService;

  public ServiceFactory() {
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
