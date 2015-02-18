package com.excilys.formation.java.webservice.impl;

import java.util.ArrayList;

import javax.jws.WebService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.PageWrapper;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.webservice.CompanyWebService;

@WebService(endpointInterface = "com.excilys.formation.java.webservice.CompanyWebService")
public class CompanyWebServiceImpl implements CompanyWebService {

  private CompanyDBService companyDBService;

  public CompanyWebServiceImpl(CompanyDBService companyDBService) {
    this.companyDBService = companyDBService;
  }

  @Override
  public ArrayList<Company> getAll() {
    return (ArrayList<Company>) companyDBService.getAll();
  }

  @Override
  public Company getOne(long l) {
    return companyDBService.getOne(l);
  }

  @Override
  public void delete(long l) {
    companyDBService.delete(l);
  }

  @Override
  public PageWrapper<Company> createPage(int page) {
    Page<Company> p = companyDBService.createPage("", new PageRequest(page, 10));
    PageWrapper<Company> pageWrapper = new PageWrapper<Company>();
    pageWrapper.setList(p.getContent());
    pageWrapper.setPageNumber(p.getNumber());
    pageWrapper.setNbResultsPerPage(p.getSize());
    pageWrapper.setNbResults((int) p.getTotalElements());
    return pageWrapper;
  }

}
