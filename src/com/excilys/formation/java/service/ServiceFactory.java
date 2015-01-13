package com.excilys.formation.java.service;

public class ServiceFactory {

  private final static ServiceFactory factory = new ServiceFactory();

  ServiceInterface                    serviceInterface;

  private ServiceFactory() {
    serviceInterface = Service.getInstance();
  }

  public static ServiceFactory getInstance() {
    return factory;
  }

  public ServiceInterface getService() {
    return serviceInterface;
  }
}
