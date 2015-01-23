package com.excilys.formation.java.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.mapper.DtoMapper;
import com.excilys.formation.java.mapper.impl.ComputerDtoMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.service.ServiceFactory;
import com.excilys.formation.java.validator.Validator;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ComputerDBService computerDBService;
  private CompanyDBService  companyDBService;
  private ServiceFactory    service;
  
  private DtoMapper<ComputerDto, Computer> computerDtoMapper;

  private Logger            logger           = LoggerFactory.getLogger(EditComputer.class);

  /**
   * @see HttpServlet#HttpServlet()
   */
  public EditComputer() {
    super();
    service = ServiceFactory.getInstance();
    computerDBService = service.getComputerDBService();
    companyDBService = service.getCompanyDBService();
    computerDtoMapper = new ComputerDtoMapper();
  }

  /**
   * Update the information of a computer (corresponding to the id in the url)
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    long id = 0;
    String idComputer = request.getParameter("id");
    
    if (Validator.isID(idComputer)) {
      id = Long.valueOf(idComputer);

      Computer computer = computerDBService.getOne(id);
      
      ComputerDto computerDto = computerDtoMapper.toDto(computer);
      
      request.setAttribute("computer", computerDto);
    }

    List<Company> companies = companyDBService.getAll();
    request.setAttribute("companies", companies);

    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/editComputer.jsp");

    dispatcher.forward(request, response);

  }

  /**
   * Update a computer in the database and redirect to the dashboard
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Computer computer = updateComputer(request);

    if (computer != null) {
      computerDBService.update(computer);

      logger.info("Computer updated with success");

      response.sendRedirect("dashBoard");
    } else {
      doGet(request, response);
    }
  }

  /**
   * Display the information of a computer corresponding to the id in the url
   * @param request
   * @return
   */
  public Computer updateComputer(HttpServletRequest request) {
    Map<String, String> error = new HashMap<String, String>();

    Computer computer;

    String idString = request.getParameter("computerId");

    if (!Validator.isID(idString)) {
      error.put("computerId", "Incorrect id");
      request.setAttribute("error", error);
      return null;
    }

    Long id = Long.valueOf(idString);

    //Check if a computer with this id exist in the database
    if (computerDBService.getOne(id) == null) {
      error.put("computerId", "No computer found");
      request.setAttribute("error", error);
      return null;
    }

    ComputerDto.Builder dtoBuilder = ComputerDto.builder();

    String name = request.getParameter("name").trim();
    if(!name.trim().isEmpty()){
      dtoBuilder.name(name);
    }
    String introduced = request.getParameter("introduced").trim();
    if(!introduced.trim().isEmpty()){
      dtoBuilder.introduced(introduced);
    }
    String discontinued = request.getParameter("discontinued").trim();
    if(!discontinued.trim().isEmpty()){
      dtoBuilder.discontinued(discontinued);
    }
    String companyId = request.getParameter("companyId").trim();
    
    ComputerDto computerDto = dtoBuilder.build();
    
    Company company = null;
    if (!companyId.trim().equals("0") || companyId != null) {
      if (Validator.isID(companyId)) {
        company = companyDBService.getOne(Long.valueOf(companyId));
      } else {
        error.put("companyId", "Incorrect Company identifier");
      }
    }

    if (Validator.isComputerDTO(computerDto, error)) {
      computer = computerDtoMapper.fromDto(computerDto);
      computer.setCompany(company);
    } else {
      request.setAttribute("error", error);
      return null;
    }

    computer.setId(id);
    return computer;
  }
}
