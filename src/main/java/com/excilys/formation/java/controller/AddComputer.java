package com.excilys.formation.java.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.mapper.DtoMapper;
import com.excilys.formation.java.mapper.impl.ComputerDtoMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.validator.Validator;

/**
 * Servlet implementation class AddComputer
 */
@Controller
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
  private static final long                serialVersionUID = 1L;

  @Autowired
  private ComputerDBService                computerDBService;
  @Autowired
  private CompanyDBService                 companyDBService;
  
  private DtoMapper<ComputerDto, Computer> computerDtoMapper = new ComputerDtoMapper();

  private Logger                           logger           = LoggerFactory
                                                                .getLogger(AddComputer.class);

  /**
   * Instantiation of the services 
   * @see HttpServlet#HttpServlet()
   */
  public AddComputer() {
    super();
  }
  
  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /**
   * Instantiation of the list of companies
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<Company> companies = companyDBService.getAll();

    request.setAttribute("companies", companies);

    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");

    dispatcher.forward(request, response);
  }

  /**
   * Add a computer in the database and redirect to the dashboard
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Computer computer = addComputer(request);

    if (computer != null) {

      computerDBService.create(computer);

      logger.info("Computer added with success");

      response.sendRedirect("dashBoard");

    } else {

      doGet(request, response);

    }
  }

  /**
   * Return a computer based on the request
   * If there was an error, set a map of error in the request and return null
   * @param request
   * @return A computer or null if there was an error
   */
  public Computer addComputer(HttpServletRequest request) {
    ComputerDto.Builder dtoBuilder = ComputerDto.builder();

    Map<String, String> error = new HashMap<String, String>();

    String name = request.getParameter("name").trim();
    if (!name.trim().isEmpty()) {
      dtoBuilder.name(name);
    }
    String introduced = request.getParameter("introduced").trim();
    if (!introduced.trim().isEmpty()) {
      dtoBuilder.introduced(introduced);
    }
    String discontinued = request.getParameter("discontinued").trim();
    if (!discontinued.trim().isEmpty()) {
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
      Computer computer = computerDtoMapper.fromDto(computerDto);
      computer.setCompany(company);
      return computer;
    } else {
      request.setAttribute("error", error);
      return null;
    }

  }
}
