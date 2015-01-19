package com.excilys.formation.java.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.service.ServiceFactory;
import com.excilys.formation.java.validator.Validator;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private ComputerDBService computerDBService;
  private CompanyDBService  companyDBService;
  private ServiceFactory    service;

  /**
   * Instantiation of the services 
   * @see HttpServlet#HttpServlet()
   */
  public AddComputer() {
    super();
    service = ServiceFactory.getInstance();
    companyDBService = service.getCompanyDBService();
    computerDBService = service.getComputerDBService();
  }

  /**
   * 
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
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Computer computer = addComputer(request);

    if (computer != null) {
      computerDBService.create(computer);
      response.sendRedirect("DashBoard");
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
    Computer.Builder builder = Computer.builder();

    Map<String, String> error = new HashMap<String, String>();

    String name = request.getParameter("name");
    if (Validator.isName(name)) {
      builder.name(name);
    } else {
      error.put("name", "Incorrect name : you must enter a name");
    }

    String introduced = request.getParameter("introduced");
    if(!introduced.trim().isEmpty()){
      if (Validator.isDate(introduced)) {
        builder.introduced(LocalDate.parse(introduced, DateTimeFormatter.ISO_LOCAL_DATE));
      } else {
        error.put("introduced", "Incorrect date format : yyyy-mm-dd");
      }
    }

    String discontinued = request.getParameter("discontinued");
    if(!discontinued.trim().isEmpty()){
      if (Validator.isDate(discontinued)) {
        builder.discontinued(LocalDate.parse(discontinued, DateTimeFormatter.ISO_LOCAL_DATE));
      } else {
        error.put("discontinued", "Incorrect date format : yyyy-mm-dd");
      }
    }

    String companyId = request.getParameter("companyId");

    if(!companyId.trim().equals("0") || !companyId.trim().isEmpty()){
      if (Validator.isID(companyId)) {
        Company company = companyDBService.getOne(Long.valueOf(companyId));
        if (company != null) {
          builder.company(company);
        }
      } else {
        error.put("companyId", "Incorrect Company identifier");
      }
    }

    if (error.isEmpty()) {
      return builder.build();
    }else{
      request.setAttribute("error", error);
      return null;
    }

  }
}
