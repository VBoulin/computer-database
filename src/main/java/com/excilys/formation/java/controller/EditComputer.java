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

  private Logger            logger           = LoggerFactory.getLogger(EditComputer.class);

  /**
   * @see HttpServlet#HttpServlet()
   */
  public EditComputer() {
    super();
    service = ServiceFactory.getInstance();
    computerDBService = service.getComputerDBService();
    companyDBService = service.getCompanyDBService();
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
      request.setAttribute("computer", computer);
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

    Computer.Builder builder = Computer.builder();

    String name = request.getParameter("name");
    if (Validator.isName(name)) {
      builder.name(name);
    } else {
      error.put("name", "Incorrect name : you must enter a name");
    }

    String introduced = request.getParameter("introduced");
    if (!introduced.trim().isEmpty()) {
      if (Validator.isDate(introduced)) {
        builder.introduced(LocalDate.parse(introduced, DateTimeFormatter.ISO_LOCAL_DATE));
      } else {
        error.put("introduced", "Incorrect date format : yyyy-mm-dd");
      }
    }

    String discontinued = request.getParameter("discontinued");
    if (!discontinued.trim().isEmpty()) {
      if (Validator.isDate(discontinued)) {
        builder.discontinued(LocalDate.parse(discontinued, DateTimeFormatter.ISO_LOCAL_DATE));
      } else {
        error.put("discontinued", "Incorrect date format : yyyy-mm-dd");
      }
    }

    String companyId = request.getParameter("companyId");

    if (!companyId.trim().equals("0") || !companyId.trim().isEmpty()) {
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
      computer = builder.build();
    } else {
      request.setAttribute("error", error);
      return null;
    }

    computer.setId(id);
    return computer;
  }
}
