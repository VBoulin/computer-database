package com.excilys.formation.java.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.excilys.formation.java.service.ComputerDBService;

/**
 * Servlet implementation class DeleteComputer
 */
@Controller
@WebServlet("/delete")
public class DeleteComputer extends HttpServlet {
  private static final long    serialVersionUID = 1L;

  @Autowired
  private ComputerDBService    computerDBService;

  private static final Pattern PATTERN          = Pattern.compile("\\d{1,19}");

  private static Logger        logger           = LoggerFactory.getLogger(DeleteComputer.class);

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteComputer() {
    super();
  }
  
  @Override
  public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
  }

  /**
   * Get the String containing the Ids of the computers to delete 
   * and Create a matcher to find the positives longs in the String
   * For each long found, delete the computer
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String selection = request.getParameter("selection");

    Matcher m = PATTERN.matcher(selection);
    String id;

    while (m.find()) {
      id = m.group();
      computerDBService.delete(Long.valueOf(id));
      logger.info("Id of the deleted computer =" + id);
    }

    response.sendRedirect("dashBoard");
  }

}
