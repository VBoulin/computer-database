package com.excilys.formation.java.service;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;

public interface ComputerDBService {
  Computer getOneComputer(Long id);

  void createComputer(Computer c);

  void updateComputer(Computer c);

  void deleteComputer(Long id);

  Page<Computer> getPagedListComputer(Page<Computer> page);

}
