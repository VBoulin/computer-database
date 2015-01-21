package com.excilys.formation.java.service;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.service.ComputerDBService;

@RunWith(MockitoJUnitRunner.class)
public class TestComputerDBService {
  ComputerDBService computerDBService;
  ComputerDao       computerDao;
  Computer          computer;
  Page<Computer>    page;
  Page<Computer>    pageR;

  @Before
  public void setUp() {
    computerDao = Mockito.mock(ComputerDao.class);
    computerDBService = new MockComputerDBService(computerDao);

    page = new Page<Computer>();
    pageR = new Page<Computer>(1, new ArrayList<Computer>(), 10, 12);

    Mockito.when(computerDao.getOne(1l)).thenReturn(Computer.builder().id(1l).name("truc").build());
    Mockito.when(computerDao.createPage(page)).thenReturn(pageR);
  }

  /**
   * test the getOne() method
   */
  @Test
  public void testGetOne() {
    Assert.assertEquals(Computer.builder().id(1L).name("truc").build(),
        computerDBService.getOne(1L));
  }

  /**
   * test the create() method
   */
  @Test
  public void testCreate() {
    computerDBService.create(computer);
    Mockito.verify(computerDao).create(computer);
  }

  /**
   * test the update() method
   */
  @Test
  public void testUpdate() {
    computerDBService.update(computer);
    Mockito.verify(computerDao).update(computer);
  }

  /**
   * test the delete() method
   */
  @Test
  public void testDelete() {
    computerDBService.delete(1l);
    Mockito.verify(computerDao).delete(1l);
  }

  /**
   * test the createpage() method
   */
  @Test
  public void testCreatePage() {
    Assert.assertEquals(pageR, computerDBService.createPage(page));
  }
}
