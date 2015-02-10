package com.excilys.formation.java.persistence;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;

public interface ComputerDao extends CrudRepository<Computer, Long>{
  
  @Modifying
  @Query(value = "DELETE computer FROM computer WHERE computer.company_id = ?1", nativeQuery = true)
  public void deleteByCompany(long id);
}
