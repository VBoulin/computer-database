package com.excilys.formation.java.controller;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestAddComputer {
  private WebDriver driver;
  
  @Before
  public void setUp() {
    driver = new FirefoxDriver();
  }

  @Test
  public void addComputer() {
    driver.get("http://localhost:8080/webapp/addcomputer");

    WebElement name = driver.findElement(By.name("name"));
    name.sendKeys("NameTest");

    WebElement introduced = driver.findElement(By.name("introduced"));
    introduced.sendKeys("2014-02-03");

    WebElement discontinued = driver.findElement(By.name("discontinued"));
    discontinued.sendKeys("2015-03-06");

    WebElement company = driver.findElement(By.name("companyId"));
    List<WebElement> companies = company.findElements(By.tagName("option"));

    WebElement companyOption = companies.get(1);
    companyOption.click();

    driver.findElement(By.id("submit")).click();
  }
  
  @Test
  public void addEmptyComputer() {
    driver.get("http://localhost:8080/webapp/addComputer");

    driver.findElement(By.id("submit")).click();
  }
  
  @Test
  public void addWrongDate() {
    driver.get("http://localhost:8080/webapp/addComputer");

    WebElement name = driver.findElement(By.name("name"));
    name.sendKeys("NameTest");

    WebElement introduced = driver.findElement(By.name("introduced"));
    introduced.sendKeys("2014-02-03Blablabla");

    WebElement discontinued = driver.findElement(By.name("discontinued"));
    discontinued.sendKeys("2015-03-06");

    WebElement company = driver.findElement(By.name("companyId"));
    List<WebElement> companies = company.findElements(By.tagName("option"));

    WebElement companyOption = companies.get(1);
    companyOption.click();

    driver.findElement(By.id("submit")).click();
  }

  @After
  public void tearDown() {
    //Close the browser
    driver.close();
  }
}
