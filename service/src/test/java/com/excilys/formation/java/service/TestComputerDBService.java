package com.excilys.formation.java.service;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.PageWrapper;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.service.ComputerDBService;

@RunWith(MockitoJUnitRunner.class)
public class TestComputerDBService {
  ComputerDBService computerDBService;
  ComputerDao       computerDao;
  Computer          computer = new Computer();
  PageWrapper<Computer>    page;
  PageWrapper<Computer>    pageR;

  @Before
  public void setUp() {
    computerDao = Mockito.mock(ComputerDao.class);
    computerDBService = new MockComputerDBService(computerDao);

    page = new PageWrapper<Computer>();
    pageR = new PageWrapper<Computer>(1, new ArrayList<Computer>(), 10, 12);

    Mockito.when(computerDao.findOne(1l)).thenReturn(Computer.builder().id(1l).name("truc").build());
    //Mockito.when(computerDao.createPage(page)).thenReturn(pageR);
  }

  /**
   * test the getOne() method
   */
  @Test
  public void testGetOne() {
    Assert.assertEquals(Computer.builder().id(1L).name("truc").build(),
        computerDBService.getOne(1L));
    
    try {
      computerDBService.getOne(-10L);
      Assert.fail("Should throw exception when the id is a negative number");
    }catch(IllegalArgumentException aExp){
      assert(aExp.getMessage().contains("negative"));
    }
    
    try {
      computerDBService.getOne(null);
      Assert.fail("Should throw exception when the id is null");
    }catch(IllegalArgumentException aExp){
      assert(aExp.getMessage().contains("null"));
    }
  }

  /**
   * test the create() method
   */
  @Test
  public void testCreate() {
    computerDBService.create(computer);
    Mockito.verify(computerDao).save(computer);
    
    try {
      computerDBService.create(null);
      Assert.fail("Should throw exception when the computer is null");
    }catch(IllegalArgumentException aExp){
      assert(aExp.getMessage().contains("null"));
    }
  }

  /**
   * test the update() method
   */
  @Test
  public void testUpdate() {
    computerDBService.update(computer);
    Mockito.verify(computerDao).save(computer);
    
    try {
      computerDBService.update(null);
      Assert.fail("Should throw exception when the computer is null");
    }catch(IllegalArgumentException aExp){
      assert(aExp.getMessage().contains("null"));
    }
  }

  /**
   * test the delete() method
   */
  @Test
  public void testDelete() {
    computerDBService.delete(1l);
    Mockito.verify(computerDao).delete(1l);
    
    try {
      computerDBService.delete(-10L);
      Assert.fail("Should throw exception when the id is a negative number");
    }catch(IllegalArgumentException aExp){
      assert(aExp.getMessage().contains("negative"));
    }
    
    try {
      computerDBService.delete(null);
      Assert.fail("Should throw exception when the id is null");
    }catch(IllegalArgumentException aExp){
      assert(aExp.getMessage().contains("null"));
    }
  }

  /**
   * test the createpage() method
   */
  @Test
  public void testCreatePage() {
    Assert.assertEquals(pageR, computerDBService.createPage(page));
    
    try {
      computerDBService.createPage(null);
      Assert.fail("Should throw exception when the page is null");
    }catch(IllegalArgumentException aExp){
      assert(aExp.getMessage().contains("null"));
    }
  }
}
