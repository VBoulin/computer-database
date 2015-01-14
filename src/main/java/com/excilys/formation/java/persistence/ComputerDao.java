package com.excilys.formation.java.persistence;

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
  Page<Computer> getPagedList(Page<Computer> page);
}
