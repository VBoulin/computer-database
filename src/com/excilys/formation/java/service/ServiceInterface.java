package com.excilys.formation.java.service;

import java.util.List;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;

public interface ServiceInterface {
  List<Computer> getAllComputers();

  List<Company> getAllCompanies();

  Computer getOneComputer(Long id);

  Company getOneCompany(Long id);

  void createComputer(Computer c);

  void updateComputer(Computer c);

  void deleteComputer(Long id);
}
