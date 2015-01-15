package com.excilys.formation.java.service;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;

public interface ComputerDBService {
	/**
	 * Retrieve one computer
	 * @param id Id of the computer that need to be retrieved
	 * @return Computer corresponding to the id
	 */
  Computer getOne(Long id);

  /**
   * Create one computer
   * @param c computer that needs to be created
   */
  void create(Computer c);

  /**
   * Updated a computer
   * @param c computer that needs to be updated
   */
  void update(Computer c);

  /**
   * Delete a computer
   * @param id Id corresponding to the computer that need to be deleted
   */
  void delete(Long id);

  /**
   * Create a page containing a list of computers
   * @param page Raw page with only basics informations such as number of results per page
   * @return Page with a list
   */
  Page<Computer> createPage(Page<Computer> page);

}
