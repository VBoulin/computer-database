package com.excilys.formation.java.service;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;

public interface CompanyDBService {

  Company getOneCompany(Long id);

  Page<Company> getPagedListCompany(Page<Company> page);

}
