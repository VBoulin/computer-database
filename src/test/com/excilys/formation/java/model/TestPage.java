package com.excilys.formation.java.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestPage{
  
  private Page page;

  @Before
  public void setUp() {
    page = new Page();
    
    page.setNbResults(11);
  }
  
  @Test
  public void testNextPage(){
    Assert.assertEquals(false,page.previousPage());
    Assert.assertEquals(true,page.nextPage());
    Assert.assertEquals(false,page.nextPage());
  }
}
