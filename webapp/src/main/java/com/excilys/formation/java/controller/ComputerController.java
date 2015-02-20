package com.excilys.formation.java.controller;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.dto.ComputerDtoMapper;
import com.excilys.formation.java.dto.DtoMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;

@Controller
public class ComputerController {
  
  @Autowired
  private MessageSource messageSource;

  @Autowired
  private ComputerDBService                computerDBService;
  @Autowired
  private CompanyDBService                 companyDBService;
  
  private DtoMapper<ComputerDto, Computer> computerDtoMapper = new ComputerDtoMapper();

  private static final Pattern PATTERN          = Pattern.compile("\\d{1,19}");

  private Logger                           logger            = LoggerFactory
                                                                 .getLogger(ComputerController.class);

  /**
   * Instantiation of the list of companies
   */
  @RequestMapping(value="/addcomputer", method = RequestMethod.GET)
  protected String addGet(Model model) {
    List<Company> companies = companyDBService.getAll();
    model.addAttribute("computerDto", new ComputerDto());
    model.addAttribute("companies", companies);
    return "addComputer";
  }

  /**
   * Add a computer in the database and redirect to the dashboard
   */
  @RequestMapping(value="/addcomputer", method = RequestMethod.POST)
  protected String addPost(Model model, @Valid ComputerDto computerDto, BindingResult rs) {
    Company company = null;
    Computer computer = null;

    Locale locale = LocaleContextHolder.getLocale();    
    String format = messageSource.getMessage("dateFormat", null, locale);
    logger.info("format : "+format);
    
    if (!rs.hasErrors()) {
      computer = computerDtoMapper.fromDto(computerDto, format);
      long id = computerDto.getIdCompany();
      if (id > 0) {
        company = companyDBService.getOne(computerDto.getIdCompany());
      }
      computer.setCompany(company);
      computerDBService.create(computer);
      logger.info("Computer added with success");
      return "redirect:/dashboard";
    } else {
      model.addAttribute("companies", companyDBService.getAll());
      return "addComputer";
    }
  }
  
  /**
   * Update the information of a computer (corresponding to the id in the url)
   */
  @RequestMapping(value="/editcomputer", method = RequestMethod.GET)
  protected String editGet(Model model, @RequestParam long id) {
    Locale locale = LocaleContextHolder.getLocale();    
    String format = messageSource.getMessage("dateFormat", null, locale);
    logger.info("format : "+format);
    
    Computer computer = computerDBService.getOne(id);
    ComputerDto computerDto = computerDtoMapper.toDto(computer, format);
    model.addAttribute("computerDto", computerDto);
    List<Company> companies = companyDBService.getAll();
    model.addAttribute("companies", companies);
    return "editComputer";
  }


  /**
   * Update a computer in the database and redirect to the dashboard
   */
  @RequestMapping(value="/editcomputer", method = RequestMethod.POST)
  protected String editPost(Model model, @Valid ComputerDto computerDto, BindingResult rs) {
    Computer computer = null;
    Company company = null;
    
    Locale locale = LocaleContextHolder.getLocale();    
    String format = messageSource.getMessage("dateFormat", null, locale);
    logger.info("format : "+format);
    
    if (!rs.hasErrors()) {
      computer = computerDtoMapper.fromDto(computerDto, format);
      long id = computerDto.getIdCompany();
      if(id > 0){
        company=companyDBService.getOne(computerDto.getIdCompany());
      }
      computer.setCompany(company);
      computerDBService.update(computer);
      logger.info("Computer updated with success");
      return "redirect:/dashboard";
    } else {
      model.addAttribute("computerDto", computerDto);
      model.addAttribute("companies", companyDBService.getAll());
      logger.info("Incorrect edition !");
      return "editComputer";
    }
  }
  
  /**
   * Get the String containing the Ids of the computers to delete 
   * and Create a matcher to find the positives longs in the String
   * For each long found, delete the computer
   */
  @RequestMapping(value="/delete", method = RequestMethod.POST)
  protected String delete(@RequestParam("selection") String selection){

    Matcher m = PATTERN.matcher(selection);
    String id;

    while (m.find()) {
      id = m.group();
      computerDBService.delete(Long.valueOf(id));
      logger.info("Id of the deleted computer =" + id);
    }

    return "redirect:/dashboard";
  }

  /**
  * ExceptionHandler that redirect any error catch to a custom error page
  */
  @ExceptionHandler(Exception.class)
  public String handleAllException(Exception ex) {
    return "error";
  }
}
