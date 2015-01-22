package com.excilys.formation.java.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.service.ServiceFactory;
import com.excilys.formation.java.validator.Validator;

/**
 * Servlet implementation class DashBoard
 */
@WebServlet("/dashBoard")
public class DashBoard extends HttpServlet {
  private static final long        serialVersionUID = 1L;

  private static ComputerDBService computerDBService;
  private ServiceFactory           service;

  private Logger                   logger           = LoggerFactory.getLogger(DashBoard.class);

  private Boolean                  orderAsc         = false;

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

    if (nbRs != null) {
      nbResults = Integer.valueOf(nbRs);
    }

    if (nbResults < 10) {
      page.setNbResultsPerPage(10);
    } else {
      page.setNbResultsPerPage(nbResults);
    }

    String search = request.getParameter("search");
    if (search == null) {
      page.setSearch("");
    } else {
      page.setSearch(search.trim());
    }

    page = computerDBService.createPage(page);

    int nbPages = 0;

    nbPages = page.getNbTotalPage();

    String sortBy = request.getParameter("order");

    if (sortBy != null) {
      sortElement(page, sortBy);
    }

    logger.info("Page created with success");

    request.setAttribute("nbPages", nbPages);

    request.setAttribute("page", page);

    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");

    dispatcher.forward(request, response);
  }

  /**
   * Sort the list of elements of a page
   * @param page Page containing the list of elements
   * @param order Parameter by which the list is sorted
   */
  public void sortElement(Page<Computer> page, String sortBy) {
    List<Computer> list = page.getList();
    for (Computer c : list) {
      c.setComparison(sortBy);
    }
    Collections.sort(list);

    orderAsc = !orderAsc;

    if (!orderAsc) {
      Collections.reverse(list);
    }

    logger.info("List of computer sorted with success");
  }

}
