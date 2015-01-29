package com.excilys.formation.java.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.java.service.ComputerDBService;

/**
 * Servlet implementation class DeleteComputer
 */
@Controller
@RequestMapping("/delete")
public class DeleteComputer{

  @Autowired
  private ComputerDBService    computerDBService;

  private static final Pattern PATTERN          = Pattern.compile("\\d{1,19}");

  private static Logger        logger           = LoggerFactory.getLogger(DeleteComputer.class);

  /**
   * Get the String containing the Ids of the computers to delete 
   * and Create a matcher to find the positives longs in the String
   * For each long found, delete the computer
   */
  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(@RequestParam("selection") String selection){

    Matcher m = PATTERN.matcher(selection);
    String id;

    while (m.find()) {
      id = m.group();
      computerDBService.delete(Long.valueOf(id));
      logger.info("Id of the deleted computer =" + id);
    }

    return "redirect:/dashboard";
  }

}
