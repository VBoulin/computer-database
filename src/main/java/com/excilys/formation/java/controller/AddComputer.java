package com.excilys.formation.java.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.mapper.DtoMapper;
import com.excilys.formation.java.mapper.impl.ComputerDtoMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;

@Controller
@RequestMapping("/addcomputer")
public class AddComputer {

  @Autowired
  private ComputerDBService                computerDBService;
  @Autowired
  private CompanyDBService                 companyDBService;

  private DtoMapper<ComputerDto, Computer> computerDtoMapper = new ComputerDtoMapper();

  private Logger                           logger            = LoggerFactory
                                                                 .getLogger(AddComputer.class);

  /**
   * Instantiation of the list of companies
   */
  @RequestMapping(method = RequestMethod.GET)
  protected String doGet(Model model) {
    List<Company> companies = companyDBService.getAll();

    model.addAttribute("computerDto", new ComputerDto());
    model.addAttribute("companies", companies);

    return "addComputer";
  }

  /**
   * Add a computer in the database and redirect to the dashboard
   */
  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(Model model, @Valid ComputerDto computerDto, BindingResult rs) {
    Company company = null;
    Computer computer = null;

    if (!rs.hasErrors()) {
      computer = computerDtoMapper.fromDto(computerDto);
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
  * ExceptionHandler that redirect any error catch to a custom error page
  */
  @ExceptionHandler(Exception.class)
  public String handleAllException(Exception ex) {
    return "error";
  }
}
