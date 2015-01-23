package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;

public interface ComputerDao {

  /**
   * Get one computer
   * @param id : id of the computer in the database
   * @return the computer found or null
   */
  Computer getOne(Long id);

  /**
   * Find all the computers
   * @return a list of computers
   */
  List<Computer> getAll();
  
  /**
   * Create a new computer
   * @param computer : computer that needs to be added in the database
   */
  void create(Computer computer);

  /**
   * Update a computer
   * @param computer : computer that needs to be updated in the database
   */
  void update(Computer computer);

  /**
   * Delete a computer
   * @param id : id of the computer that needs to be deleted
   */
  void delete(Long id);

  /**
   * Create one page by requesting the necessary informations
   * @param page Previous page
   * @return page Next page requested containing all the necessary informations
   */
  Page<Computer> createPage(Page<Computer> page);
  
  /**
   * Delete a computer based on his company ID
   */
  void deleteByCompany(Long id, Connection conn);
}
