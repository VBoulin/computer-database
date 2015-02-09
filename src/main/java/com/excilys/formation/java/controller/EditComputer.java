package com.excilys.formation.java.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.mapper.DtoMapper;
import com.excilys.formation.java.mapper.impl.ComputerDtoMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;

/**
 * Servlet implementation class EditComputer
 */
@Controller
@RequestMapping("/editcomputer")
public class EditComputer {
  
  @Autowired
  private ComputerDBService                computerDBService;
  @Autowired
  private CompanyDBService                 companyDBService;

  private DtoMapper<ComputerDto, Computer> computerDtoMapper = new ComputerDtoMapper();

  private Logger                           logger            = LoggerFactory
                                                                 .getLogger(EditComputer.class);

  /**
   * Update the information of a computer (corresponding to the id in the url)
   */
  @RequestMapping(method = RequestMethod.GET)
  protected String doGet(Model model, @RequestParam long id) {

    Computer computer = computerDBService.getOne(id);

    ComputerDto computerDto = computerDtoMapper.toDto(computer);

    model.addAttribute("computer", computerDto);

    List<Company> companies = companyDBService.getAll();

    model.addAttribute("companies", companies);

    return "editComputer";
  }


  /**
   * Update a computer in the database and redirect to the dashboard
   */
  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(Model model, @Valid ComputerDto computerDto, BindingResult rs) {
    Computer computer = null;
    Company company = null;
    
    if (!rs.hasErrors()) {
      computer = computerDtoMapper.fromDto(computerDto);
      long id = computerDto.getIdCompany();
      if(id > 0){
        company=companyDBService.getOne(computerDto.getIdCompany());
      }
      computer.setCompany(company);
      computerDBService.update(computer);
      logger.info("Computer updated with success");
      return "redirect:/dashboard";
    } else {
        model.addAttribute("companies", companyDBService.getAll());
        return "editcomputer";
    }
  }
}
