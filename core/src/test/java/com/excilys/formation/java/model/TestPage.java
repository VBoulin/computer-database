package com.excilys.formation.java.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestPage {

  private PageWrapper<Computer> page;

  @Before
  public void setUp() {
    page = new PageWrapper<Computer>();

    page.setNbResults(11);
  }

  /**
   * Test the pagination functions
   */
  @Test
  public void testNextAndPreviousPage() {
    Assert.assertEquals(false, page.previousPageOrFirst());
    Assert.assertEquals(true, page.nextPage());
    Assert.assertEquals(false, page.nextPage());
    Assert.assertEquals(true, page.previousPageOrFirst());
  }
}
