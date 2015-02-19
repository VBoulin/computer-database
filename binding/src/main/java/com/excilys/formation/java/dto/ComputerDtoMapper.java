package com.excilys.formation.java.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.excilys.formation.java.model.Computer;

/**
 * Computer DTO mapper
 * Convert a computer DTO into a computer and vice-versa
 * @author Vincent
 */
public class ComputerDtoMapper implements DtoMapper<ComputerDto, Computer> {
  
  /**
   * {@inheritDoc}
   */
  public Computer fromDto(ComputerDto dto, String format) {
    Computer.Builder builder = Computer.builder();
    builder.id(dto.getId()).name(dto.getName());
    
    if (!StringUtils.isEmpty(dto.getIntroduced())) {
      builder.introduced(LocalDate.parse(dto.getIntroduced(),  DateTimeFormatter.ofPattern(format)));
    }
    if (!StringUtils.isEmpty(dto.getDiscontinued())) {
      builder
          .discontinued(LocalDate.parse(dto.getDiscontinued(),  DateTimeFormatter.ofPattern(format)));
    }
    
    return builder.build();
  }

  /**
   * {@inheritDoc}
   */
  public List<Computer> fromDto(List<ComputerDto> dtos, String format) {
    List<Computer> computers = dtos.stream().map(dto -> {
      Computer computer = fromDto(dto, format);
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
  public ComputerDto toDto(Computer computer, String format) {
    ComputerDto.Builder builder = ComputerDto.builder();
    builder.id(computer.getId()).name(computer.getName());
    
    if (computer.getIntroduced() != null) {
      builder.introduced(computer.getIntroduced().format(DateTimeFormatter.ofPattern(format)));
    }
    if (computer.getDiscontinued() != null) {
      builder.discontinued(computer.getDiscontinued().format(DateTimeFormatter.ofPattern(format)));
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
  public List<ComputerDto> toDto(List<Computer> computers, String format) {
    List<ComputerDto> dtos = computers.stream().map(computer -> {
      ComputerDto dto = toDto(computer, format);
      if (dto != null) {
        return dto;
      } else {
        return null;
      }
    }).collect(Collectors.toList());
    return dtos;
  }
}
