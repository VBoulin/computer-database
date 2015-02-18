package com.excilys.formation.java.webservice;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.PageWrapper;

@WebService
@SOAPBinding(style = Style.RPC)
public interface CompanyWebService {

  @WebMethod
  ArrayList<Company> getAll();

  @WebMethod
  Company getOne(long l);

  @WebMethod
  void delete(long l);

  @WebMethod
  PageWrapper<Company> createPage(int page);
}
