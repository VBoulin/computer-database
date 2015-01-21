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
  public void editComputer() {
    driver.get("http://localhost:8080/computer-database/dashBoard?page=1&nbResults=10");

  }

  @After
  public void tearDown() {
    driver.close();
  }
}
