package com.excilys.formation.java.webservice.impl;

import java.util.ArrayList;

import javax.jws.WebService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.dto.ComputerDtoMapper;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.PageWrapper;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.webservice.ComputerWebService;

@WebService(endpointInterface="com.excilys.formation.java.webservice.ComputerWebService")
public class ComputerWebServiceImpl implements ComputerWebService {

    private ComputerDBService computerDBService;
    private ComputerDtoMapper computerDtoMapper;
    
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    public ComputerWebServiceImpl(ComputerDBService computerDBService, CompanyDBService companyDBService) {
        this.computerDBService = computerDBService;
        this.computerDtoMapper = new ComputerDtoMapper();
    }

    @Override
    public ComputerDto getOne(long id) {
        Computer computer = computerDBService.getOne(id);
        if (computer == null) {
            return new ComputerDto();
        }
        return computerDtoMapper.toDto(computer, DATE_FORMAT);
    }
    
    @Override
    public ArrayList<ComputerDto> getAll() {
        return (ArrayList<ComputerDto>) computerDtoMapper.toDto(computerDBService.getAll(),DATE_FORMAT);
    }

    @Override
    public void create(ComputerDto computerDTO) {
            computerDBService.create(computerDtoMapper.fromDto(computerDTO, DATE_FORMAT));
    }

    @Override
    public void update(ComputerDto computerDTO) {
            computerDBService.update(computerDtoMapper.fromDto(computerDTO, DATE_FORMAT));
    }

    @Override
    public PageWrapper<ComputerDto> createPage(int page) {
        Page<Computer> p = computerDBService.createPage("", new PageRequest(page, 10));
        PageWrapper<ComputerDto> pageWrapper = new PageWrapper<ComputerDto>();
        pageWrapper.setList(computerDtoMapper.toDto(p.getContent(), DATE_FORMAT));
        pageWrapper.setPageNumber(p.getNumber());
        pageWrapper.setNbResultsPerPage(p.getSize());
        pageWrapper.setNbResults((int) p.getTotalElements());
        return pageWrapper;
    }

    @Override
    public void delete(long id) {
        computerDBService.delete(id);
    }
    
}
