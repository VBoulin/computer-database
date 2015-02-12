package com.excilys.formation.java.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.java.model.Computer;

public interface ComputerDao extends CrudRepository<Computer, Long>{
  
  public void deleteByCompanyId(long id);
  public int countByNameContainingOrCompanyNameContaining(String name, String companyName);
  public Page<Computer> findByNameContainingOrCompanyNameContaining(String name, String companyName, Pageable pageable);
}
