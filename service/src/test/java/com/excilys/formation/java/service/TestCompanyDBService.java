package com.excilys.formation.java.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.service.impl.CompanyDBServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class TestCompanyDBService {

    @InjectMocks
    CompanyDBServiceImpl companyDBService;
    
    CompanyDao companyDao;
    ComputerDao computerDao;
    
    List<Computer> computers;
    List<Company> companies;
    
    Company company1;
    Company company2;
    Company company3;
    
    @Before
    public void init() {
        companyDao = mock(CompanyDao.class);
        computerDao = mock(ComputerDao.class);
        
        companies = new ArrayList<Company>();
        
        company1 = new Company(1L, "company 1");
        company2 = new Company(2L, "company 2");
        company3 = new Company(3L, "company 3");
        
        companies.add(company1);
        companies.add(company2);
        companies.add(company3);
        
        computers = new ArrayList<Computer>();
        
        computers.add(new Computer(1L, "computer 1", null, null, company1));
        computers.add(new Computer(2L, "computer 2", null, null, company2));
        computers.add(new Computer(3L, "computer 3", null, null, company2));
        
        
        when(companyDao.findAll()).thenReturn(companies);
        
        when(companyDao.findOne(1L)).thenReturn(company1);
        
        doAnswer(new Answer<List<Computer>>() {
            @Override
            public List<Computer> answer(InvocationOnMock invocation) {
                long l = (Long) invocation.getArguments()[0];
                companies.removeIf(c -> c.getId() == l);
                return null;
            }
        }).when(companyDao).delete(anyLong());
        
        doAnswer(new Answer<List<Computer>>() {
            @Override
            public List<Computer> answer(InvocationOnMock invocation) {
                long l = (Long) invocation.getArguments()[0];
                computers.removeIf(c -> c.getCompany().getId() == l);
                return null;
            }
        }).when(computerDao).deleteByCompanyId(anyLong());
        
        MockitoAnnotations.initMocks(this);
    }
    
    
    /*
     * Test getAll function
     */
    @Test
    public void getAll() {
        assertEquals(companies, companyDBService.getAll());
    }
    
    
    /*
     * Tests getOne function
     */
    @Test
    public void getOne() {
        assertEquals(company1, companyDBService.getOne(1L));
    }
    
    @Test
    public void getOneInvalid() {
        assertNull(companyDBService.getOne(-1L));
    }
    
    
    /*
     * Tests delete function
     */
    @Test
    public void delete() {
        int companiesSize = companies.size();
        int computersSize = computers.size();
        
        companyDBService.delete(1L);
        
        companiesSize--;
        computersSize--;
        
        assertEquals(companiesSize, companies.size());
        assertEquals(computersSize, computers.size());
    }
    
    @Test
    public void deleteInvalidId() {
      int companiesSize = companies.size();
      int computersSize = computers.size();
        companyDBService.delete(null);
        
        assertEquals(companiesSize, companies.size());
        assertEquals(computersSize, computers.size());
    }
}