package com.excilys.formation.java.mapper.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.mapper.DtoMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.validator.Validator;

public class ComputerDtoMapper implements DtoMapper<ComputerDto, Computer> {

  /**
   * {@inheritDoc}
   */
  public Computer fromDto(ComputerDto dto) {
    if (!Validator.isComputerDTO(dto)) {
      return null;
    }

    Computer.Builder builder = Computer.builder();
    builder.id(dto.getId()).name(dto.getName());

    if (dto.getIntroduced() != null) {
      builder.introduced(LocalDate.parse(dto.getIntroduced(), DateTimeFormatter.ISO_LOCAL_DATE));
    }
    if (dto.getDiscontinued() != null) {
      builder
          .discontinued(LocalDate.parse(dto.getDiscontinued(), DateTimeFormatter.ISO_LOCAL_DATE));
    }
    if (dto.getIdCompany() != null) {
      builder.company(new Company(Long.parseLong(dto.getIdCompany()), dto.getCompanyName()));
    }
    return builder.build();
  }

  /**
   * {@inheritDoc}
   */
  public List<Computer> fromDto(List<ComputerDto> dtos) {
    List<Computer> computers = dtos.stream().map(dto -> {
      Computer computer = fromDto(dto);
      if (computer != null) {
        return computer;
      } else {
        return null;
      }
    }).collect(Collectors.toList());
    return computers;
  }

  /**
   * {@inheritDoc}
   */
  public ComputerDto toDto(final Computer computer) {
    ComputerDto.Builder builder = ComputerDto.builder();
    builder.id(computer.getId()).name(computer.getName());

    if (computer.getIntroduced() != null) {
      builder.introduced(computer.getIntroduced().toString());
    }
    if (computer.getDiscontinued() != null) {
      builder.discontinued(computer.getDiscontinued().toString());
    }
    if (computer.getCompany() != null) {
      builder.idCompany(String.valueOf(computer.getCompany().getId()));
      builder.companyName(computer.getCompany().getName());
    }

    return builder.build();
  }

  /**
   * {@inheritDoc}
   */
  public List<ComputerDto> toDto(List<Computer> computers) {
    List<ComputerDto> dtos = computers.stream().map(computer -> {
      ComputerDto dto = toDto(computer);
      if (dto != null) {
        return dto;
      } else {
        return null;
      }
    }).collect(Collectors.toList());
    return dtos;
  }

  /**
   * {@inheritDoc}
   */
  public Page<ComputerDto> toDto(Page<Computer> page) {
    Page<ComputerDto> pageReturn = new Page<ComputerDto>();
    pageReturn.setNbResults(page.getNbResults());
    pageReturn.setNbResultsPerPage(page.getNbResultsPerPage());
    pageReturn.setPageNumber(page.getPageNumber());
    pageReturn.setOrder(page.getOrder());
    pageReturn.setSort(page.getSort());
    pageReturn.setSearch(page.getSearch());
    pageReturn.setList(toDto(page.getList()));
    return pageReturn;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<Computer> fromDto(Page<ComputerDto> page) {
    Page<Computer> pageReturn = new Page<Computer>();
    pageReturn.setNbResults(page.getNbResults());
    pageReturn.setNbResultsPerPage(page.getNbResultsPerPage());
    pageReturn.setPageNumber(page.getPageNumber());
    pageReturn.setOrder(page.getOrder());
    pageReturn.setSort(page.getSort());
    pageReturn.setSearch(page.getSearch());
    pageReturn.setList(fromDto(page.getList()));
    return pageReturn;
  }
}
