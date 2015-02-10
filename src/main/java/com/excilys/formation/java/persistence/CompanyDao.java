package com.excilys.formation.java.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;

public interface CompanyDao extends CrudRepository<Company, Long>{
  
}
