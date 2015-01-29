package com.excilys.formation.java.controller;

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
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.OrderBy;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.model.SortBy;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.validator.Validator;

@Controller
@RequestMapping("/dashboard")
public class DashBoard {

  @Autowired
  private ComputerDBService                computerDBService;

  private DtoMapper<ComputerDto, Computer> computerDtoMapper = new ComputerDtoMapper();

  private Logger                           logger            = LoggerFactory
                                                                 .getLogger(DashBoard.class);

  /**
   * Creation of a page based on the request
   */
  @RequestMapping(method = RequestMethod.GET)
  protected String doGet(Model model, @RequestParam(defaultValue="0", value="page") String pageNum,
      @RequestParam(defaultValue="0", value="nbResults") String nbRs, @RequestParam(defaultValue="", value="search") String search,
      @RequestParam(defaultValue="", value="sort") String sortBy, @RequestParam(defaultValue="", value="order") String orderBy) {
    Page<Computer> page = new Page<Computer>();

    int pageNumber = 0;

    if (Validator.isInt(pageNum)) {
      pageNumber = Integer.valueOf(pageNum);
    }

    if (pageNumber < 1) {
      page.setPageNumber(1);
    } else {
      page.setPageNumber(pageNumber);
    }

    int nbResults = 0;

    if (nbRs != null) {
      nbResults = Integer.valueOf(nbRs);
    }

    if (nbResults < 10) {
      page.setNbResultsPerPage(10);
    } else {
      page.setNbResultsPerPage(nbResults);
    }

    if (search == null) {
      page.setSearch("");
    } else {
      page.setSearch(search.trim());
    }

    SortBy sort = SortBy.getInstance(sortBy);

    if (sort == null) {
      sort = SortBy.ID;
    }

    page.setSort(sort);

    if (orderBy != null) {
      orderBy = orderBy.toUpperCase();
    }

    OrderBy order = OrderBy.getInstance(orderBy);

    if (order == null) {
      order = OrderBy.ASC;
    }

    page.setOrder(order);

    page = computerDBService.createPage(page);
    logger.info("Page created with success");

    int nbPages = 0;

    nbPages = page.getNbTotalPage();

    model.addAttribute("nbPages", nbPages);

    Page<ComputerDto> pageDto = computerDtoMapper.toDto(page);

    model.addAttribute("page", pageDto);


    return "dashboard";
  }

}
