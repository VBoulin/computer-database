package com.excilys.formation.java.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.mapper.DtoMapper;
import com.excilys.formation.java.mapper.impl.ComputerDtoMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.validator.Validator;

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

  private long                             id                = 0;

  /**
   * Update the information of a computer (corresponding to the id in the url)
   */
  @RequestMapping(method = RequestMethod.GET)
  protected String doGet(Model model,
      @RequestParam(defaultValue = "0", value = "id") String idComputer) {

    idComputer = idComputer.trim();

    if (Validator.isID(idComputer)) {
      id = Long.valueOf(idComputer);

      Computer computer = computerDBService.getOne(id);

      ComputerDto computerDto = computerDtoMapper.toDto(computer);

      model.addAttribute("computer", computerDto);
    }

    List<Company> companies = companyDBService.getAll();

    model.addAttribute("companies", companies);

    return "editComputer";
  }

  /**
   * Update a computer in the database and redirect to the dashboard
   */
  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(Model model, @RequestParam("name") String name,
      @RequestParam("introduced") String introduced,
      @RequestParam("discontinued") String discontinued, @RequestParam("companyId") String companyId) {
    Computer computer = updateComputer(model, name, introduced, discontinued, companyId);

    if (computer != null) {
      computerDBService.update(computer);
      logger.info("Computer updated with success");
    } else {
      logger.info("Fail to update the computer");
    }

    return "redirect:/dashboard";
  }

  /**
   * Display the information of a computer corresponding to the id in the url
   * @param request
   * @return A computer edited
   */
  public Computer updateComputer(Model model, String name, String introduced, String discontinued,
      String companyId) {
    Map<String, String> error = new HashMap<String, String>();

    Computer computer;

    //Check if a computer with this id exist in the database
    if (computerDBService.getOne(id) == null) {
      error.put("computerId", "No computer found");
      model.addAttribute("error", error);
      return null;
    }

    ComputerDto.Builder dtoBuilder = ComputerDto.builder();

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
      computer = computerDtoMapper.fromDto(computerDto);
      computer.setCompany(company);
    } else {
      model.addAttribute("error", error);
      return null;
    }

    computer.setId(id);
    return computer;
  }
}
