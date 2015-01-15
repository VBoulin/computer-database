package com.excilys.formation.java.service;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;

public interface CompanyDBService {

	/**
	 * Retrieve one company
	 * @param id Id of the company that needs to be retrieved
	 * @return The company corresponding to the id
	 */
  Company getOne(Long id);

  /**
   * Create a page containing a list of companies
   * @param page Raw page with only basics informations such as number of results per page
   * @return Page with a list
   */
  Page<Company> createPage(Page<Company> page);

}
