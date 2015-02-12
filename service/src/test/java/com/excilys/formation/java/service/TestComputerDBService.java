package com.excilys.formation.java.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.service.impl.ComputerDBServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestComputerDBService {
 
    @InjectMocks
    ComputerDBServiceImpl computerDBService;
    
    ComputerDao computerDao;
    
    List<Computer> computers;
    
    Company company1;
    Company company2;
    Company company3;
    
    Computer computer;
 
    @Before
    public void before() {
        computerDao = mock(ComputerDao.class);
 
        company1 = new Company(1L, "company 1");
        company2 = new Company(2L, "company 2");
        company3 = new Company(3L, "company 3");
 
        computers = new ArrayList<Computer>();
        
        computers.add(new Computer(1L, "computer 1", null, null, company1));
        computers.add(new Computer(2L, "computer 2", null, null, company2));
        computers.add(new Computer(3L, "computer 3", null, null, company2));
 
        computer = new Computer(4L, "computer 4", null, null, company3);
 
        when(computerDao.findAll()).thenReturn(computers);
 
        doAnswer(new Answer<Computer>() {
            @Override
            public Computer answer(InvocationOnMock invocation) {
                long l = (Long) invocation.getArguments()[0];
                if (l > 0 && l < computers.size()) {
                   return computers.get((int) l - 1);
                }
                return null;
            }
        }).when(computerDao).findOne(anyLong());
        
        doAnswer(new Answer<Computer>() {
            @Override
            public Computer answer(InvocationOnMock invocation) {
                Computer computer = (Computer) invocation.getArguments()[0];
                if (computer != null && computer.getId() > 0 && computer.getId() < computers.size()) {
                    computers.set( computer.getId().intValue() - 1, computer);
                }
                return null;
            }
            
        }).when(computerDao).save(any(Computer.class));
        
        doAnswer(new Answer<Computer>() {
            @Override
            public Computer answer(InvocationOnMock invocation) {
                long l = (Long) invocation.getArguments()[0];
                computers.removeIf(c -> c.getId() == l);
                return null;
            }
            
        }).when(computerDao).delete(anyLong());
 
        MockitoAnnotations.initMocks(this);
    }
    
    /*
     * Test getAll function
     */
    @Test
    public void getAll() {
        assertEquals(computers, computerDBService.getAll());
    }
 
    /*
     * Tests getOne function
     */
    @Test
    public void getOne() {
        assertEquals(computers.get(0), computerDBService.getOne(1L));
    }
 
    @Test
    public void getOneInvalid() {
        assertNull(computerDBService.getOne(-1L));
        assertNull(computerDBService.getOne(5L));
    }
    
    /*
     * Tests of create function
     */    
    @Test
    public void createNull() {
        computerDBService.create(null);
        assertEquals(computers, computerDBService.getAll());
    }
    
    /*
     * Tests of update function
     */
    @Test
    public void update() {
        computerDBService.update(computer);
        assertEquals(computers, computerDBService.getAll());
    }
    
    @Test
    public void updateNull() {
        computerDBService.update(null);
        assertEquals(computers, computerDBService.getAll());
    } 
    
    /*
     * Tests createPage function
     */
    @Test
    public void createPageNull() {
        assertNull(computerDBService.createPage(null));
    }  
    
    /*
     * Tests of delete function
     */
    @Test
    public void delete() {
        int x = computerDBService.getAll().size();
        
        computerDBService.delete(3L);
        
        x--;
        
        assertEquals(x, computers.size());
    }
    
    @Test
    public void deleteInvalidId() {
        int x = computerDBService.getAll().size();
        
        computerDBService.delete((long) -1);
        computerDBService.delete(null);
        
        assertEquals(x, computers.size());
    }
 }