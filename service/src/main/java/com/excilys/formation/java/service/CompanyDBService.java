package com.excilys.formation.java.service;

import java.util.List;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.PageWrapper;

public interface CompanyDBService {

	/**
	 * Retrieve one company
	 * @param id Id of the company that needs to be retrieved
	 * @return The company corresponding to the id
	 */
  Company getOne(Long id);
  
  /**
   * Find all the companies
   * @return a list of companies
   */
  List<Company> getAll();

  /**
   * Create a page containing a list of companies
   * @param page Raw page with only basics informations such as number of results per page
   * @return Page with a list
   */
  PageWrapper<Company> createPage(PageWrapper<Company> page);
  
  /**
   * Delete a company and all the computer referencing that company
   * @param id id of the company
   */
  void delete(Long id);

}
