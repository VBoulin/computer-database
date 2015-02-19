package com.excilys.formation.java.webservice;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.model.PageWrapper;

/**
 * Webservice for the computers
 * @author Vincent
 *
 */
@WebService
public interface ComputerWebService {

  /**
   * Retrieve on computer
   * @param id id of the computer
   * @return A computerDto corresponding to the id
   */
  @WebMethod
  ComputerDto getOne(long id);
  
  /**
   * Retrieve all the computerDto
   * @return A list containing all the DTO
   */
  @WebMethod
  ArrayList<ComputerDto> getAll();

  /**
   * Insert a computer based on the computer DTO (parameter)
   * @param computerDTO 
   */
  @WebMethod
  void create(ComputerDto computerDTO);

  /**
   * Update a computer based on the computer DTO
   * @param computerDTO
   */
  @WebMethod
  void update(ComputerDto computerDTO);

  /**
   * Delete a computer corresponding to the id
   * @param id id of the computer that needs to be deleted
   */
  @WebMethod
  void delete(long id);

  /**
   * Create a page containing a list of computers and other informations
   * @param page page number
   * @return page created based on the page number
   */
  @WebMethod
  PageWrapper<ComputerDto> createPage(int page);
}
