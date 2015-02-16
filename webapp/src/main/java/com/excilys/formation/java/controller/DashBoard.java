package com.excilys.formation.java.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.dto.DtoMapper;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.ComputerDBService;

@Controller
@RequestMapping("/dashboard")
public class DashBoard {

  @Autowired
  private ComputerDBService                computerDBService;
  @Autowired
  private DtoMapper<ComputerDto, Computer> computerDtoMapper;
  @Autowired
  private MessageSource messageSource;

  private Logger                           logger            = LoggerFactory
                                                                 .getLogger(DashBoard.class);

  /**
   * Creation of a page based on the request
   */
  @RequestMapping(method = RequestMethod.GET)
  protected String doGet(Model model, @PageableDefault(page=0,value=10) Pageable pageable, 
      @RequestParam(defaultValue = "", required = false, value = "search") String search){
    Page<Computer> page = computerDBService.createPage(search, pageable);
    
    Locale locale = LocaleContextHolder.getLocale();    
    String format = messageSource.getMessage("dateFormat", null, locale);
    logger.info("format : "+format);
    
    if (page.getSort() != null) {
      model.addAttribute("sort", SortBy.getSortBy(page.getSort()).getSort());
      model.addAttribute("order", SortBy.getSortBy(page.getSort()).getOrder());
    }
    model.addAttribute("search", search);
    model.addAttribute("page", page);
    
    List<ComputerDto> computers = computerDtoMapper.toDto(page.getContent(), format);
    model.addAttribute("computers", computers);
    
    logger.info("Page created with sucess");    
    return "dashboard";
  }
  
  /**
   * ExceptionHandler that redirect any error catch to a custom error page
   */
   @ExceptionHandler(Exception.class)
   public String handleAllException(Exception ex) {
     logger.info("Error in dashboard.");
     return "error";
   }

}
