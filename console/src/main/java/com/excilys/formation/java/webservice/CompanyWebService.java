package com.excilys.formation.java.webservice;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.PageWrapper;

/**
 * Webservice for the companies
 * @author Vincent
 */
@WebService
public interface CompanyWebService {

  /**
   * Retrieve all the company
   * @return A list of companies
   */
  @WebMethod
  ArrayList<Company> getAll();

  /**
   * Retrieve one company
   * @param l id of the company
   * @return the company corresponding to the id
   */
  @WebMethod
  Company getOne(long l);

  /**
   * Delete one company
   * @param l id of the company that have to be deleted
   */
  @WebMethod
  void delete(long l);

  /**
   * Create a page containing a list of companies and other informations
   * @param page page number
   * @return page created based on the page number
   */
  @WebMethod
  PageWrapper<Company> createPage(int page);
}
