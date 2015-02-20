package com.excilys.formation.java.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.java.model.Company;

/**
 * Dao for the companies
 * @author Vincent
 *
 */
public interface CompanyDao extends CrudRepository<Company, Long>{
  
  /**
   * Count the number of companies containing the string, passed into the parameter, in their name
   * @param name String that have to be searched
   * @return The number of computers
   */
  int countByNameContaining(String name);
  /**
   * Retrieve a page of companies containing the string, passed into the parameter, in their name
   * @param name String that have to be searched
   * @param pageable 
   * @return Page of computers
   */
  Page<Company> findByNameContaining(String name, Pageable pageable);
}
