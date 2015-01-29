package com.excilys.formation.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.excilys.formation.java.validator.Validator;

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

    model.addAttribute("companies", companies);

    return "addComputer";
  }

  /**
   * Add a computer in the database and redirect to the dashboard
   */
  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(Model model, @RequestParam("name") String name,
      @RequestParam("introduced") String introduced,
      @RequestParam("discontinued") String discontinued, @RequestParam("companyId") String companyId) {

    Computer computer = addComputer(model, name, introduced, discontinued, companyId);

    if (computer != null) {

      computerDBService.create(computer);

      logger.info("Computer added with success");

      return "redirect:/dashboard";

    } else {

      return doGet(model);

    }
  }

  /**
   * Return a computer based on the request
   * If there was an error, set a map of error in the request and return null
   * @return A computer or null if there was an error
   */
  public Computer addComputer(Model model, String name, String introduced, String discontinued, String companyId) {
    ComputerDto.Builder dtoBuilder = ComputerDto.builder();

    Map<String, String> error = new HashMap<String, String>();

    name = name.trim();
    if (!name.trim().isEmpty()) {
      dtoBuilder.name(name);
    }
    introduced = introduced.trim();
    if (!introduced.trim().isEmpty()) {
      dtoBuilder.introduced(introduced);
    }
    discontinued = discontinued.trim();
    if (!discontinued.trim().isEmpty()) {
      dtoBuilder.discontinued(discontinued);
    }
    companyId = companyId.trim();

    ComputerDto computerDto = dtoBuilder.build();

    Company company = null;
    if (!companyId.trim().equals("0") || companyId != null) {
      if (Validator.isID(companyId)) {
        company = companyDBService.getOne(Long.valueOf(companyId));
      } else {
        error.put("companyId", "Incorrect Company identifier");
      }
    }

    if (Validator.isComputerDTO(computerDto, error)) {
      Computer computer = computerDtoMapper.fromDto(computerDto);
      computer.setCompany(company);
      return computer;
    } else {
      model.addAttribute("error", error);
      return null;
    }

  }
}
