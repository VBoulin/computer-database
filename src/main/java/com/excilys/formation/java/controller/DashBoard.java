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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.mapper.DtoMapper;
import com.excilys.formation.java.mapper.impl.ComputerDtoMapper;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.OrderBy;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.model.SortBy;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.service.ServiceFactory;
import com.excilys.formation.java.validator.Validator;

/**
 * Servlet implementation class DashBoard
 */
@Controller
@WebServlet("/dashBoard")
public class DashBoard extends HttpServlet {
  private static final long                serialVersionUID = 1L;

  @Autowired
  private ComputerDBService                computerDBService;
  
  private DtoMapper<ComputerDto, Computer> computerDtoMapper = new ComputerDtoMapper();

  private Logger                           logger           = LoggerFactory
                                                                .getLogger(DashBoard.class);

  /**
   * Instantiation of the services
   * @see HttpServlet#HttpServlet()
   */
  public DashBoard() {
    super();
  }

  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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

    String sortBy = request.getParameter("sort");

    SortBy sort = SortBy.getInstance(sortBy);

    if (sort == null) {
      sort = SortBy.ID;
    }

    page.setSort(sort);

    String orderBy = request.getParameter("order");

    if (orderBy != null) {
      orderBy = orderBy.toUpperCase();
    }

    OrderBy order = OrderBy.getInstance(orderBy);

    if (OrderBy.getInstance("ASC").equals(order)) {
      order = OrderBy.DESC;
    } else {
      order = OrderBy.ASC;
    }
    if (order == null) {
      order = OrderBy.ASC;
    }

    page.setOrder(order);

    page = computerDBService.createPage(page);
    logger.info("Page created with success");

    int nbPages = 0;

    nbPages = page.getNbTotalPage();

    request.setAttribute("nbPages", nbPages);

    Page<ComputerDto> pageDto = computerDtoMapper.toDto(page);

    request.setAttribute("page", pageDto);

    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");

    dispatcher.forward(request, response);
  }

}
