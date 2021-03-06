package com.excilys.formation.java.webservice;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.model.PageWrapper;

@WebService
public interface ComputerWebService {

  @WebMethod
  ComputerDto getOne(long id);

  @WebMethod
  ArrayList<ComputerDto> getAll();

  @WebMethod
  void create(ComputerDto computerDTO);

  @WebMethod
  void update(ComputerDto computerDTO);

  @WebMethod
  void delete(long id);

  @WebMethod
  PageWrapper<ComputerDto> createPage(int page);
}
