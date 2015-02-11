package com.excilys.formation.java.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.java.model.Company;

public interface CompanyDao extends CrudRepository<Company, Long>{

  int countByNameContaining(String name);
  Page<Company> findByNameContaining(String name, Pageable pageable);
}
