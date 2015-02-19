package com.excilys.formation.java.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.dto.ComputerDtoMapper;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.ComputerDBService;

@RestController
@RequestMapping("/computer")
public class RestComputerController {
  @Autowired
  private ComputerDBService   computerDBService;

  private ComputerDtoMapper   computerDtoMapper = new ComputerDtoMapper();

  private static final String DATE_FORMAT       = "yyyy-MM-dd";

  @RequestMapping(value = "/pageNum/{pageNum}/size/{size}", method = RequestMethod.GET)
  public Page<ComputerDto> getComputers(@PathVariable int pageNum, @PathVariable int size) {
    Pageable pageable = new PageRequest(pageNum, size);
    Page<Computer> page = computerDBService.createPage("", pageable);
    Page<ComputerDto> pageReturned = new PageImpl<ComputerDto>(computerDtoMapper.toDto(
        page.getContent(), DATE_FORMAT), pageable, page.getTotalElements());
    return pageReturned;
  }
}