package com.excilys.formation.java.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.service.ServiceFactory;
import com.excilys.formation.java.validator.Validator;

/**
 * Servlet implementation class DashBoard
 */
@WebServlet("/DashBoard")
public class DashBoard extends HttpServlet {
  private static final long        serialVersionUID = 1L;

  private static ComputerDBService computerDBService;
  private ServiceFactory           service;
  
  private Logger logger = LoggerFactory.getLogger(DashBoard.class);

  /**
   * Instantiation of the services
   * @see HttpServlet#HttpServlet()
   */
  public DashBoard() {
    super();
    service = ServiceFactory.getInstance();
    computerDBService = service.getComputerDBService();
  }

  /**
   * Creation of a page based on the request
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Page<Computer> page = new Page<Computer>();

    String pageNb = request.getParameter("page");
    int pageNumber = 0;

    if (Validator.isInt(pageNb)) {
      pageNumber = Integer.valueOf(pageNb);
    }

    if (pageNumber < 1) {
      page.setPageNumber(1);
    } else {
      page.setPageNumber(pageNumber);
    }

    String nbRs = request.getParameter("nbResults");
    int nbResults = 0;

    if (nbRs!=null) {
      nbResults = Integer.valueOf(nbRs);
    }

    if (nbResults < 10) {
      page.setNbResultsPerPage(10);
    } else {
      page.setNbResultsPerPage(nbResults);
    }

    page = computerDBService.createPage(page);
    
    int nbPages=0;
    
    if (page.getNbResultsPerPage() != 0) {
      nbPages = page.getNbResults() / page.getNbResultsPerPage();
      if (page.getNbResults() % page.getNbResultsPerPage() != 0) {
          nbPages++;
      }
    }

    logger.info("Page created with success");
    
    request.setAttribute("nbPages", nbPages);

    request.setAttribute("page", page);

    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");

    dispatcher.forward(request, response);
  }

}
