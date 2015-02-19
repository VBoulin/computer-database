package com.excilys.formation.java.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestDashBoard {
  private WebDriver driver;

  @Before
  public void setUp() {
    driver = new FirefoxDriver();
  }

  @Test
  public void validInformations() {
    driver.get("http://localhost:8080/webapp/dashboard?page=1&size=10");

  }
  
  @Test
  public void negativePage() {
    driver.get("http://localhost:8080/webapp/dashboard?page=-1&size=10");
  }
  
  @Test
  public void negativeResults() {
    driver.get("http://localhost:8080/webapp/dashboard?page=1&size=-15");
  }

  @After
  public void tearDown() {
    driver.close();
  }
}
