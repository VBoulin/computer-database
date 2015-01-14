package com.excilys.formation.java.persistence;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;

public interface CompanyDao {

  /**
   * Get one company corresponding to his id
   * @param id : id of the company in the database
   * @return the company found or null
   */
  Company getOne(Long id);

  /**
   * Create one page by requesting the necessary informations
   * @param page Previous page
   * @return page Next page requested containing all the necessary informations
   */
  Page<Company> getPagedList(Page<Company> page);
}
