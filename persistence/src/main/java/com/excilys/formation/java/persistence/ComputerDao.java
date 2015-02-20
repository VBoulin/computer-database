package com.excilys.formation.java.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.java.model.Computer;

public interface ComputerDao extends CrudRepository<Computer, Long>{
  
  /**
   * @param id
   */
  public void deleteByCompanyId(long id);
  /**
   * Retrieve a page of computers containing the string, passed into the parameter, in their name
   * @param name
   * @param companyName
   * @param pageable
   * @return
   */
  public Page<Computer> findByNameContainingOrCompanyNameContaining(String name, String companyName, Pageable pageable);
}
