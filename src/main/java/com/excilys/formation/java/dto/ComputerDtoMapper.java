package com.excilys.formation.java.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.PageWrapper;

public class ComputerDtoMapper implements DtoMapper<ComputerDto, Computer> {

  /**
   * {@inheritDoc}
   */
  public Computer fromDto(ComputerDto dto) {
    Computer.Builder builder = Computer.builder();
    builder.id(dto.getId()).name(dto.getName());

    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(LocaleContextHolder.getLocale());
    
    if (!StringUtils.isEmpty(dto.getIntroduced())) {
      builder.introduced(LocalDate.parse(dto.getIntroduced(), formatter));
    }
    if (!StringUtils.isEmpty(dto.getDiscontinued())) {
      builder
          .discontinued(LocalDate.parse(dto.getDiscontinued(), formatter));
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
      builder.idCompany(computer.getCompany().getId());
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
  public PageWrapper<ComputerDto> toDto(PageWrapper<Computer> page) {
    PageWrapper<ComputerDto> pageReturn = new PageWrapper<ComputerDto>();
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
  public PageWrapper<Computer> fromDto(PageWrapper<ComputerDto> page) {
    PageWrapper<Computer> pageReturn = new PageWrapper<Computer>();
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
